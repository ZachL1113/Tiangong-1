const BASE_URL = '';


/** 
 * @param {string} game
 * @param {number} [level]
 * @returns {Promise<Object>}
*/
export async function getNewGame(game, level = null) {
  let uri = `/games/${game}/new`;
  if (game === 'huarongdao' && level !== null) {
    uri += `?level=${level}`;
  }
  return await getData(uri);
}

/**
 * 发送用户移动操作
 * @param {string} game - 游戏名，如 "2048" 或 "huarongdao"
 * @param {string} boardJson - 当前棋盘状态（JSON 字符串）
 * @param {string} direction - 方向：U / D / L / R（或 W/A/S/D）
 * @param {number} pieceId - 棋子编号（仅部分游戏使用）
 * @returns {Promise<Object>} 返回 { boardJson, valid, success }
 */
export async function sendMove(game, boardJson, direction, pieceId) {
  return await postData(`/games/${game}/move`, {
    boardJson,
    direction,
    pieceId,
  });
}

async function getData(uri) {
  try {
    const response = await fetch(BASE_URL + uri);
    if (!response.ok) throw new Error('网络响应不正常');
    return await response.json();
  } catch (error) {
    console.error('获取数据失败:', error);
    throw error;
  }
}

async function postData(uri, params) {
  try {
    const response = await fetch(BASE_URL + uri, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(params),
    });
    if (!response.ok) throw new Error('网络响应不正常');
    return await response.json();
  } catch (error) {
    console.error('发送数据失败:', error);
    throw error;
  }
}
