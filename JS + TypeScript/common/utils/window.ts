let windowFocus = true;

window.onblur = function() { windowFocus = false; };
window.onfocus = function() { windowFocus = true; };

const isWindowActive = () => {
 return windowFocus && !document.hidden; 
};

export { isWindowActive }