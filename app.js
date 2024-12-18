import express from 'express';
import bodyParser from 'body-parser';
import cors from 'cors';
import swaggerUi from 'swagger-ui-express';

import webRoutes from './routes/webRoutes.js';
import mobileRoutes from './routes/mobileRoutes.js';
import swaggerDocs from './configs/swaggerConfig.js';

const app = express();

app.use(cors());
app.use(bodyParser.json());

// Swagger Documentation
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerDocs));

// Web Routes
app.use('/web', webRoutes);
// Mobile Routes
app.use('/mobile', mobileRoutes);

export default app;
