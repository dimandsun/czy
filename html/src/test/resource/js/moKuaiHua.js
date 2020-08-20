export let title = '测试';
export function test() {
  console.log('模块化编程测试');
}

let size = 'A';
class Person {
  constructor(name, gender) {
    this.name = name;
    this.gender = gender;
  }
  say() {
    console.log(this.name + ':看谁呢兄弟！');
  }
}
class Girl extends Person {
  constructor(name, gender, size) {
    super(name, gender);
    this.size = size;
  }
  set size(value) {
    this._size = value;
  }
  get size() {
    if(this._size===undefined){
      this._size='A';
    }
    return this._size;
  }
}
export{size,Person,Girl}

export default{
  age:18,
  sleep(){
    console.log('一天只需要睡4小时');
  },
  lie:function(){
    console.log('我可以撒谎');
  }
}
