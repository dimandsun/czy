import util from './util.js';

/* 
使用说明，
  属性前加: 绑定属性，包括监听事件
  文本中用 ${x}
  实例：
 <div id="app">
   <input type="text" :value="count" />
   <input type="button" value="增加" :click="add" />
   <input type="button" value="减少" :click="reduce" />
   <div title="累加器">${count}</div>
 </div>
<script type="module">
  import {Binder} from './Binder.js';
  let binder = new Binder({
    el: '#app',
    data: {
      count: 0,
    },
    methods: {
      add() {
        binder.count++;
      },
      reduce() {
        binder.count--;
      }
    }
  });
  console.log(binder);
</script>
 */
export class Binder {
  constructor({el,methods,data}) {
    this.$data = data;
    this.$methods = methods;
    /* 初始化绑定对象*/
    this._binding = {};
    /* 解析文档中出现的绑定器的变量*/
    this.$el = document.querySelector(el);
    this._compileParent(this.$el);
    const handler = this._getHandler(data);
    /* 得到代理对象*/
    return new Proxy({ ...this.$el,
      ...this.$data,
      ...this.$methods
    }, handler);
  }
  _pushWatcher(bindData, watcher) {
    if (!(bindData in this._binding)) {
      this._binding[bindData] = [];
    }
    this._binding[bindData].push(watcher);
  }
  /*
   observer的作用是能够对所有的数据进行监听操作，通过使用Proxy对象
   中的set方法来监听，如有发生变动就会拿到最新值通知订阅者。
  */
  _getHandler(datas) {
    const me = this;
    const handler = {
      set(target, bindData, value) {
        let result = false;
        /* 给数据做赋值操作*/
        if (Reflect.has(target, bindData)) {
          result = Reflect.set(target, bindData, value);
        } else {
          throw new ReferenceError(`目标${target}没有属性${bindData}`);
        }
        /* 数据绑定元素，同步更新元素*/
        if (!(bindData in me._binding)) {
          throw new ReferenceError(`绑定器没有为${bindData}的属性或方法`);
        }
        for (let watcher of [...me._binding[bindData]]) {
          watcher.update(value);
        }
        /* 返回*/
        return result;
      }
    };
    return handler;
  }
  /*
   指令解析器，对每个元素节点的指令进行扫描和解析，根据指令模板替换数据，以及绑定相对应的更新函数
  */
  _compileParent(root) {
    if (root.hasChildNodes()) {
      for (let node of [...root.children]) {
        this._compileChild(node);
      }
    } else {
      this._compileChild(root);
    }
  }
  _compileChild(node) {
    if (node.hasChildNodes()) {
      this._compileParent(node);
    }
    /* 初始化元素属性(包括监听)*/
    for (let entry of [...node.attributes]) {
      if (entry.name.indexOf(':') !== 0) {
        continue;
      }
      /* 真正的属性名*/
      let realAttr = entry.name.substr(1);
      /* 需要绑定的数据(包括方法)名 */
      let bindData = node.getAttribute(entry.name);
      if (bindData in this.$data) {
        /* 绑定的是属性*/
        /* 初始化属性*/
        node.setAttribute(realAttr, this.$data[bindData]);
        /* 注册观察者*/
        this._pushWatcher(bindData, new Watcher(node, realAttr));
      } else if (bindData in this.$methods) {
        /* 绑定的是方法*/
        /* 初始化监听方法，如果无效，改成node.addEventListener(realAttr, this.$methods[bindData]);*/
        // node.setAttribute(realAttr,this.$methods[bindData]);
        node.addEventListener(realAttr, this.$methods[bindData]);
      } else {
        /* 抛出异常*/
        throw new ReferenceError(`绑定器没有为${bindData}的数据或方法`);
      }
    }
    /* 初始化元素的文本内容*/
    let text = node['innerHTML'].trim();
    if (text.indexOf('${') !== -1) {
      let bindData = util.matchingFirstStr(/\${(?<bindData>[(a-zA-Z_)]*)}/g, text).bindData;
      if (bindData && (bindData in this.$data)) {
        /* 绑定的是文本*/
        /* 初始化文本*/
        node['innerHTML'] = this.$data[bindData];
        /* 注册观察者*/
        this._pushWatcher(bindData, new Watcher(node, 'innerHTML'));
      }
    }
  }
}
/*
 watcher的作用是 链接Observer 和 Compile的桥梁，能够订阅并收到每个属性变动的通知，
 执行指令绑定的响应的回调函数，从而更新视图。
*/
class Watcher {
  constructor(node, attr) {
    this.node = node;
    this.attr = attr;
  }
  update(value) {
    this.node[this.attr] = value;
  }
}
