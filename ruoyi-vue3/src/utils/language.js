export const getLanguage = () => {
  const language = localStorage.getItem('language')
  return language || 'zh-CN'
}

export const setLanguage = (lang) => {
  localStorage.setItem('language', lang)
  window.location.reload() // 切换语言后刷新页面，最简单有效
}