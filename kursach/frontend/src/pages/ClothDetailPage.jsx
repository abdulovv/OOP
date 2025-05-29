// src/pages/ClothDetailPage.jsx
import React from 'react';
import { useParams } from 'react-router-dom';
import { Typography, Image, Button } from 'antd';

const { Title, Paragraph } = Typography;

// Временная функция для получения деталей товара по ID
const getProductDetails = (id) => {
    const products = [
        {
            id: '1',
            name: 'Футболка базовая',
            brand: 'Generic',
            price: 25,
            description: 'Удобная базовая футболка из 100% хлопка.',
            image: 'https://via.placeholder.com/400x400/eee/000?Text=T-Shirt',
            details: {
                size: [
                    { name: 'XS', quantity: 10 },
                    { name: 'S', quantity: 15 },
                    { name: 'M', quantity: 20 },
                    { name: 'L', quantity: 12 },
                    { name: 'XL', quantity: 8 },
                ],
            },
        },
        {
            id: '2',
            name: 'Джинсы прямые',
            brand: 'Denim Co',
            price: 75,
            description: 'Классические прямые джинсы из плотного денима.',
            image: 'https://via.placeholder.com/400x400/eee/000?Text=Jeans',
            details: {
                size: [
                    { name: 'W28 L30', quantity: 5 },
                    { name: 'W30 L32', quantity: 10 },
                    { name: 'W32 L34', quantity: 7 },
                ],
            },
        },
        // Добавьте больше товаров с информацией о количестве по размерам
    ];
    return products.find(product => product.id === id);
};

function ClothDetailPage() {
    const { id } = useParams();
    const product = getProductDetails(id);

    if (!product) {
        return <div>Товар не найден</div>;
    }

    const scaleFactor = 1; // По умолчанию без масштабирования

    return (
        <div style={{ padding: 24, backgroundColor: '#fff', display: 'flex', justifyContent: 'center', alignItems: 'flex-start', transform: `scale(${scaleFactor})`, transformOrigin: 'top center' }}>
            <div style={{ display: 'flex', gap: 32 }}>
                <Image
                    width={400}
                    src={product.image}
                    alt={product.name}
                />
                <div>
                    <Title level={2}>{product.brand} {product.name}</Title>
                    <Paragraph style={{ fontSize: '1.2em', fontWeight: 'bold' }}>Цена: ${product.price}</Paragraph>
                    <Paragraph>{product.description}</Paragraph>

                    {product.details?.size && (
                        <div style={{ marginTop: 16 }}>
                            <Paragraph strong>Размер:</Paragraph>
                            {product.details.size.map(sizeInfo => (
                                <div key={sizeInfo.name} style={{ display: 'inline-block', marginRight: 8 }}>
                                    <div style={{ fontSize: '0.8em', color: '#8c8c8c', marginBottom: 2, textAlign: 'center' }}>
                                        {sizeInfo.quantity} шт.
                                    </div>
                                    <Button>{sizeInfo.name}</Button>
                                </div>
                            ))}
                        </div>
                    )}

                    <Button type="primary" size="large" style={{ marginTop: 24 }}>
                        Добавить в корзину
                    </Button>
                </div>
            </div>
        </div>
    );
}

export default ClothDetailPage;