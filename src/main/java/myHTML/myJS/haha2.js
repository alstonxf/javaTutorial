function animal (name,type){
    this.name = name;
    this.type = type;

    function itshabit(name){
        return "i like " + name;
    }
    this.habit = itshabit(this.name)
}

cat = new animal("cat","中华田园猫")
alert(cat.name)
alert(cat.type)
alert(cat.habit)

// let name = prompt("请输入你的姓名:");
// let age = prompt("请输入你的年龄:");
// alert("你的姓名是:" + name + "\n" + "你的年龄是:" + age);
// prompt("请输入你的姓名:");
//
//
//
// console.log("这是一条日志");
//
// // 将字符串类型的 name 变量改为 数字类型
// let name = '大魔王';
// console.log(name);
// name = 13;
// console.log(name);
//
//
