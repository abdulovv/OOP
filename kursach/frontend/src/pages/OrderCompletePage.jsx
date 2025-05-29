// src/pages/OrderCompletePage.jsx
import React from 'react';
import { Result, Button } from 'antd';
import { Link } from 'react-router-dom';

function OrderCompletePage() {
    return (
        <div style={{ padding: 24, backgroundColor: '#fff', textAlign: 'center' }}>
            <Result
                status="success"
                title="Заказ успешно оформлен!"
                subTitle="Мы получили ваш заказ и скоро свяжемся с вами."
                extra={[
                    <Button type="primary" key="buy">
                        <Link to="/">Продолжить покупки</Link>
                    </Button>,
                    <Button key="view">
                        <Link to="/orders">Посмотреть заказы</Link>
                    </Button>,
                ]}
            />
        </div>
    );
}

export default OrderCompletePage;