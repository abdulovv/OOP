// src/pages/OrderSuccessPage.jsx
import React from 'react';
import { Typography, Button } from 'antd';
import { Link } from 'react-router-dom';

const { Title, Paragraph } = Typography;

function OrderSuccessPage() {
    return (
        <div style={{ padding: 24, backgroundColor: '#fff', textAlign: 'center' }}>
            <Title level={2}>Заказ успешно оформлен!</Title>
            <Paragraph>Спасибо за ваш заказ. Мы свяжемся с вами в ближайшее время.</Paragraph>
            {/* TODO: Отобразить номер заказа, если он есть */}
            <Link to="/">
                <Button type="primary">Вернуться на главную</Button>
            </Link>
        </div>
    );
}

export default OrderSuccessPage;