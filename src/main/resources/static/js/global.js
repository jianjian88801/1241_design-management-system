// globalData 可以随便定义，调用的时候相同即可
var globalData ={

    /**
     *  uname 用户姓名
     *  usex 用户性别
     */
    //多个存储
    // setUserInfo:function (uname,name){
    //     //单个存储
    //     sessionStorage.setItem("uname",uname);
    //     sessionStorage.setItem("name",name);
    // },
    setUserInfo:function (uname,name,jobTitle,XueWei){
        //单个存储
        sessionStorage.setItem("uname",uname);
        sessionStorage.setItem("name",name);
        sessionStorage.setItem("jobTitle",jobTitle);
        sessionStorage.setItem("XueWei",XueWei);
    },
    //单个获取
    getUserUName:function(){
        return sessionStorage.getItem("uname");
    },
    getUserName:function(){
        return sessionStorage.getItem("name");
    },
    getUserJobtitle:function(){
        return sessionStorage.getItem("jobTitle");
    },
    getUserXueWei:function(){
        return sessionStorage.getItem("XueWei");
    }
}

