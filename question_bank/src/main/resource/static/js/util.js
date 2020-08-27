/* 去除首尾空格 */
if (typeof(String.prototype.trim) === "undefined") {
  String.prototype.trim = function() {
    return this.replace(/^\s+/, '').replace(/\s+$/, '');
  };
}
export default {
  ///\$\{(.+?)\}/g
  /**
   * \${(?<bd>[(a-zA-Z_)]*)}/g.exec(text);
   * 根据正则表达式reg，返回第一次匹配的结果。返回值是个对象
   * @param {Object} reg
   * @param {Object} str
   */
  matchingFirstStr(reg, str) {
    const result = reg.exec(str);
    if(result===null){
      console.log(`asdffasdfd--------------${reg}.exec("${str}")`);
    }
    
    return result === null ? {"groups":{}} : result.groups;
  },
  isBlank(text) {
    return typeof(text) == "undefined" || text == '' || text == null;
  },
  isNotBlank(text) {
    return !this.isBlank(text);
  },
  isIterable:(obj)=>obj != null && typeof obj[Symbol.iterator] === 'function'
  ,
  repalce(str,oldReg,prefix){
    const arr=str.match(oldReg);
    if(!arr){
      return str;
    }
    for(const item of arr){
      const temp=prefix+item;
      str=str.replace(new RegExp(item,"g"),temp);
    }
    return str;
  },
}
