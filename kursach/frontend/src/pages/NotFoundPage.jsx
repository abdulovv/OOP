// src/pages/NotFoundPage.jsx
import React from 'react';
import { Result, Button } from 'antd';
import { Link } from 'react-router-dom';

function NotFoundPage() {
    return (
        <Result
            status="404"
            title="404"
            subTitle="Страница не найдена"
            extra={<Link to="/"><Button type="primary">На главную</Button></Link>}
        />
    );
}

export default NotFoundPage;