const keys = []; 
document.onkeydown = e =>  
{if(keys.indexOf(e.which)<0) keys.push(e.which);}; 
document.onkeyup = e => {keys.splice(keys.indexOf(e.which),1);}; 
setInterval(() => { 
keys.forEach(item => { 
switch(item) { 
case 87:case 38:location.href = "/?forward";break; 
case 68:case 39:location.href = "/?right";break; 
case 65:case 37:location.href = "/?left";break; 
case 83:case 40:location.href = "/?back";break; 
}});}, 111);
