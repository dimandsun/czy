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
    let result = reg.exec(str);
    return result == null ? {} : result.groups;
  }
}
