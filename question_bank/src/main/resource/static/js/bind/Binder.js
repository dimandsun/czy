import util from '../util.js';

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
  const binder = new Binder({
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
  constructor({
    el,
    methods,
    data
  }) {
    this.bindReg=/{{(?<bindText>[(a-zA-Z_\.+=\*\/)].*)}}/g;//绑定表达式
    this.$data={
      data:data,
      eval: (code, param) => {
        let realCode=util.repalce(code,/([(a-zA-Z_)].*)/g,'param.');
        return eval(realCode);
      }
    }
    this.$methods = methods;
    /* 初始化绑定对象*/
    this._binding = {};
    /* 解析文档中出现的绑定器的变量*/
    this.$el = document.querySelector(el);
    this._compileParent(this.$el);
    const handler = this._getHandler(data);
    /* 得到代理对象*/
    return new Proxy({ ...this.$el,
      ...this.$data.data,
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
        for (const watcher of [...me._binding[bindData]]) {
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
    if (root.hasChildNodes() && root.children.length > 0) {
      for (const node of [...root.children]) {
        this._compileChild(node, true);
      }
    }
    this._compileChild(root, false);
  }
  _compileChild(node, isRecursion) {
    if (isRecursion && node.hasChildNodes() && node.children.length > 0) {
      this._compileParent(node);
    }
    // <option b-for="qType of qTypeLisst">${qType}</option>
    /* 初始化元素属性(包括监听),以:开头的属性*/
    this._compileNodeAttr(node);

    /* 初始化带有b-for属性的元素，没有b-for属性才_compileNodeText*/
    if (!this._compileNodeForeach(node)) {
      /* 初始化元素的文本内容：*/
      this._compileNodeText(node);
    }
  }
  /**
   * 初始化遍历元素。没有b-for属性(或者没有正确编译b-for)返回false，有返回true
   * @param {Object} node
   */
  _compileNodeForeach(node) {
    if (!node.hasAttribute("b-for")) {
      return false;
    }
    /* 得到b-for表达式*/
    const forGroups = util.matchingFirstStr(/(?<item>[(a-zA-Z_)]*) of (?<list>[(a-zA-Z_)]*)/g, node.getAttribute(
      "b-for"));
    const itemName = forGroups.item;
    const listName = forGroups.list;
    if (util.isBlank(itemName) || util.isBlank(listName)) {
      return false; /* b-for表达式错误 */
    }
    const list = this.$data.data[listName];
    if (!util.isIterable(list)) {
      return false; //不可遍历
    }
    node.removeAttribute("b-for");
    /* 元素的子元素，暂时不考虑此情况begin*/
    //@TODO
    /* 元素的子元素，暂时不考虑此情况end*/
    /*判断元素文本内容是否有需要绑定的数据：*/
    let notNeedBind = true;
    for (const cn of [...node.childNodes]) {
      if (cn.nodeType === 3 && cn.nodeValue.indexOf('{{') !== -1) {
        if (util.matchingFirstStr(this.bindReg, cn.nodeValue).bindText) {
          notNeedBind = false;
          break;
        }
      }
    }
    if (notNeedBind) {
      return true;
    }
    /* 给元素的文本内容赋初始值*/
    const id = node.getAttribute("id");
    const name = node.getAttribute("name");
    list.forEach((item, index, list) => {
      let itemNode = null;
      itemNode = node.cloneNode(true);
      node.parentNode.appendChild(itemNode);
      /* id、name加上index，防止id、name重复*/
      if (id) {
        itemNode.setAttribute("id", "".concat(id, index)); //"".concat防止id为数字时，拼接变成加法运算
      }
      if (name) {
        itemNode.setAttribute("name", "".concat(name, index)); //"".concat防止name为数字时，拼接变成加法运算
      }
      for (const cn of [...itemNode.childNodes]) {
        if (cn.nodeType === 3 && cn.nodeValue.indexOf('{{') !== -1) {
          const bindText = util.matchingFirstStr(this.bindReg, cn.nodeValue).bindText;
          if (bindText && (bindText.indexOf(itemName) !== -1 || bindText.indexOf(listName) !== -1 || bindText.indexOf(
              "index") !== -1)) {
            let code = bindText.replace(new RegExp(itemName, "g"), "item")
              .replace(new RegExp(listName, "g"), "list");
            cn.nodeValue = this.$data.eval(code, { ...this.$data.data,
              "item": item,
              "index": index,
              "list": list
            });
            this._pushWatcher(list, new Watcher(cn, "v-for", code));
          }
        }
      }
    });
    /* 功成身退，移除元素*/
    node.remove();
    return true;
  }
  /**
   * 初始化元素属性(包括监听)
   * @param {Object} node
   */
  _compileNodeAttr(node) {
    for (const entry of [...node.attributes]) {
      if (entry.name.indexOf(':') !== 0) {
        continue;
      }
      /* 真正的属性名*/
      const realAttr = entry.name.substr(1);
      /* 需要绑定的数据(包括方法)名 */
      const bindData = node.getAttribute(entry.name);
      if (bindData in this.$data.data) {
        /* 绑定的是属性*/
        /* 初始化属性*/
        node.setAttribute(realAttr, this.$data.data[bindData]);
        /* 注册观察者*/
        this._pushWatcher(bindData, new Watcher(node, realAttr));
      } else if (bindData in this.$methods) {
        /* 绑定的是方法*/
        /* 初始化监听方法*/
        node.addEventListener(realAttr, this.$methods[bindData]);
      } else {
        /* 抛出异常*/
        throw new ReferenceError(`绑定器没有为${bindData}的数据或方法`);
      }
    }
  }
  /**
   * 初始化元素的文本内容：innerHTML，innerText都不能满足
   * @param {Object} node
   */
  _compileNodeText(node) {
    for (const cn of [...node.childNodes]) {
      if (cn.nodeType === 3) { //文本元素
        if (cn.nodeValue.indexOf('{{') !== -1) {
          const bindText = util.matchingFirstStr(this.bindReg, cn.nodeValue).bindText;

          if (bindText) {
            /* 初始化文本*/
            cn.nodeValue = this.$data.eval(bindText, this.$data.data);
            // cn.nodeValue = this.$data.data[bindText];
            /* 注册观察者*/
            this._pushWatcher(bindText, new Watcher(cn, 'nodeValue'));
          }
        }
      }
    }
  }
}
/*
 watcher的作用是 链接Observer 和 Compile的桥梁，能够订阅并收到每个属性变动的通知，
 执行指令绑定的响应的回调函数，从而更新视图。
*/
class Watcher {
  constructor(node, attr, code) {
    this.node = node;
    this.attr = attr;
    this.code = code;
  }
  update(value) {
    if (this.node.nodeType == 3) {
      this.node.nodeValue = value;
    } else if (this.attr === "v-for") {
      const list = value;
      list.forEach((item, index, list) => {
        this.node.nodeValue = eval(this.code);
      })
    } else {
      this.node[this.attr] = value;
    }
  }
}
