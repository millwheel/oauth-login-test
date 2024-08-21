export function setCookie(name, value, second) {
  let expires = "";
  if (second) {
    const date = new Date();
    date.setTime(date.getTime() + second * 1000);
    expires = "; expires=" + date.toUTCString();
  }
  document.cookie = name + "=" + (value || "") + expires + "; path=/";
}
