const keys = [];
    document.onkeydown = e => {
        if(keys.indexOf(e.which)<0) keys.push(e.which);
    };
    document.onkeyup = e => {
        keys.splice(keys.indexOf(e.which),1);
    };

    setInterval(() => {
        keys.forEach(item => {
        switch(item) {
        case 87:case 38://W
            console.log('вперед');
            break;
        case 68:case 39://D
            console.log('вправо');
            break;
        case 65:case 37://A
            console.log('влево');
            break;
        case 83:case 40://S
            console.log('назад');
            break;
        default:
            //console.log(item);
        }
    });
    }, 111);
