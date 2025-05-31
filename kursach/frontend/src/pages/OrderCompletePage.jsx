// src/pages/OrderCompletePage.jsx
import React, { useState } from 'react';
import { Typography, Input, Button, Form } from 'antd';
import { useNavigate } from 'react-router-dom';

const { Title } = Typography;

function OrderCompletePage() {
    const navigate = useNavigate();
    const [form] = Form.useForm();
    const [loading, setLoading] = useState(false);

    const onFinish = (values) => {
        setLoading(true);
        console.log('Данные заказа:', values);
        const cartItems = JSON.parse(localStorage.getItem('shoppingCart') || '[]');
        console.log('Товары в корзине:', cartItems);

        // TODO: Отправить данные заказа на бэкенд
        // Включая values (full_name, contact_info) и cartItems

        // Очищаем корзину локально
        localStorage.removeItem('shoppingCart');

        setTimeout(() => {
            setLoading(false);
            navigate('/order-success'); // Перенаправление на страницу успешного заказа
        }, 2000);
    };

    const onFinishFailed = (errorInfo) => {
        console.log('Failed:', errorInfo);
    };

    return (
        <div style={{ padding: 24, backgroundColor: '#fff' }}>
            <Title level={2}>Оформление заказа</Title>
            <Form
                form={form}
                name="basic"
                labelCol={{ span: 8 }}
                wrapperCol={{ span: 16 }}
                initialValues={{ remember: true }}
                onFinish={onFinish}
                onFinishFailed={onFinishFailed}
                autoComplete="off"
            >
                <Form.Item
                    label="Имя"
                    name="full_name"
                    rules={[
                        { required: true, message: 'Пожалуйста, введите ваше имя' },
                        {
                            pattern: /^[а-яА-ЯёЁa-zA-Z\s]+$/,
                            message: 'Пожалуйста, введите только буквы и пробелы',
                        },
                    ]}
                >
                    <Input />
                </Form.Item>

                <Form.Item
                    label="Контактная информация(email/phone)"
                    name="contact_info"
                    rules={[
                        { required: true, message: 'Пожалуйста, введите ваш email или номер телефона' },
                        {
                            pattern: /^[0-9a-zA-Z\s.+@]+$/,
                            message: 'Пожалуйста, введите корректный email или номер телефона',
                        },
                    ]}
                >
                    <Input />
                </Form.Item>

                <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
                    <Button type="primary" htmlType="submit" loading={loading}>
                        Подтвердить заказ
                    </Button>
                </Form.Item>
            </Form>
        </div>
    );
}

export default OrderCompletePage;