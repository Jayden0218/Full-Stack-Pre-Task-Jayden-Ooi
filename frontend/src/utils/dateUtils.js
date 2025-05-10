export function formatDate(timestamp) {
  const date = new Date(timestamp)
  return date.toLocaleString()
}

export function formatToLocalDateTime(timestamp){
  const date = new Date(timestamp);
  const pad = (num) => String(num).padStart(2, '0');
  return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())} ${pad(date.getHours())}:${pad(date.getMinutes())}:${pad(date.getSeconds())}`;
};