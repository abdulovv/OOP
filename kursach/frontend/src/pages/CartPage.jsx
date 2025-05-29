// src/pages/CartPage.jsx
import React from 'react';
import { Typography, Button, List, Card, InputNumber } from 'antd';
import { Link, useNavigate } from 'react-router-dom';

const { Title, Paragraph } = Typography;

// Временные данные корзины
const cartItems = [
    {
        id: '1',
        name: 'Футболка The North Face',
        size: '52/54 RUS (L INT) - Бежевый',
        price: 191.20,
        quantity: 3,
        image: 'https://via.placeholder.com/100x100/eee/000?Text=T-Shirt', // Замените на реальное изображение
    },
    {
        id: '2',
        name: 'Футболка The North Face',
        size: '52/54 RUS (L INT) - Фиолетовый',
        price: 365.67,
        quantity: 2,
        image: 'https://via.placeholder.com/100x100/eee/000?Text=T-Shirt2', // Замените на реальное изображение
    },
];

function CartPage() {
    const navigate = useNavigate();

    const handleCheckout = () => {
        // Здесь должна быть логика оформления заказа (отправка данных на сервер и т.д.)
        // После успешного оформления перенаправляем на страницу OrderComplete
        navigate('/order-complete');
    };

    if (cartItems.length === 0) {
        return (
            <div style={{ padding: 24, backgroundColor: '#fff', textAlign: 'center' }}>
                <Title level={2}>Корзина пока пустая</Title>
                <Paragraph>Добавьте товары и оформите заказ.</Paragraph>
                <Link to="/">
                    <Button type="primary">На главную</Button>
                </Link>
            </div>
        );
    }

    const totalPrice = cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);

    return (
        <div style={{ padding: 24, backgroundColor: '#fff' }}>
            <Title level={2}>Корзина ({cartItems.length} товара)</Title>
            <List
                itemLayout="horizontal"
                dataSource={cartItems}
                renderItem={item => (
                    <List.Item>
                        <Card style={{ width: '100%', margin: '10px 0' }}>
                            <div style={{ display: 'flex', alignItems: 'center' }}>
                                <img src={item.image} alt={item.name} style={{ width: 80, height: 80, marginRight: 16, objectFit: 'cover' }} />
                                <div style={{ flexGrow: 1 }}>
                                    <Typography.Text strong>{item.name}</Typography.Text>
                                    <Paragraph type="secondary">{item.size}</Paragraph>
                                    <Typography.Text>Цена: {item.price} р.</Typography.Text>
                                </div>
                                <div style={{ display: 'flex', alignItems: 'center' }}>
                                    <Typography.Text style={{ marginRight: 8 }}>Кол-во:</Typography.Text>
                                    <InputNumber min={1} defaultValue={item.quantity} style={{ width: 70, marginRight: 16 }} />
                                    <Typography.Text strong>{(item.price * item.quantity).toFixed(2)} р.</Typography.Text>
                                </div>
                            </div>
                        </Card>
                    </List.Item>
                )}
                footer={
                    <div style={{ textAlign: 'right', marginTop: 24 }}>
                        <Typography.Title level={3}>Итого: {totalPrice.toFixed(2)} р.</Typography.Title>
                        <Button type="primary" size="large" onClick={handleCheckout}>Оформить заказ</Button>
                    </div>
                }
            />
        </div>
    );
}

export default CartPage;