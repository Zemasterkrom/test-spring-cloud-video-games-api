// Endpoints paramétrés de l'API Spring Boot pour la recherche, l'ajout, la modification, la suppression
export const environment = {
  api: {
    protocol: 'http',
    host: 'localhost',
    port: '8080',
    endpoints: {
      allVideoGames: '/video-games/all',
      oneVideoGame: '/video-games/:id',
      addVideoGame: '/video-games/add',
      updateVideoGame: '/video-games/modify/:id',
      deleteVideoGame: '/video-games/delete/:id'
    }
  }
};