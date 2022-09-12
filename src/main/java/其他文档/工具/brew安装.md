## mac上安装brew(最简易)

我们使用linux下有yum

mac相应的是brew

安装软件

brew的安装目录在 /usr/local/Cellar，我们以安装nodejs为例子，只需要执行：

brew install nodejs

就安装完了，就这么简单

接下来我们安装brew

直接使用中科大源安装brew

打开终端

 /usr/bin/ruby -e "$(curl -fsSL https://cdn.jsdelivr.net/gh/ineo6/homebrew-install/install)"

 速度如果几十k或几k,说明出问题了

control+z重新粘贴代码.

那个还不行就这个

/bin/zsh -c "$(curl -fsSL https://gitee.com/cunkai/HomebrewCN/raw/master/Homebrew.sh)"
还不行就这个

 /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install.sh)"
以上三个源都经过本人亲测可以使用.



## mac常用命令：

安装 brew install xxx

卸载 brew uninstall xxx

更新brew本身 brew update

更新所有brew安装的软件：brew upgrade

更新某一个brew安装的软件：brew upgrade xxx