import swaggerJsDoc from 'swagger-jsdoc';

const swaggerOptions = {
  definition: {
    openapi: '3.0.0', // Versi OpenAPI
    info: {
      title: 'API Dokumentasi',
      version: '1.0.0',
      description: 'Dokumentasi untuk API Express',
    },
    servers: [
      {
        url: 'http://localhost:3000', // Ganti dengan URL server API Anda
      },
    ],
  },
  apis: ['./src/routes/*.js'], // Path ke file rute untuk dokumentasi
};

const swaggerDocs = swaggerJsDoc(swaggerOptions);
export default swaggerDocs;
