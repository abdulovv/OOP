// src/components/ProductCard.jsx
import React from 'react';
import { Card } from 'antd';
import { Link } from 'react-router-dom';

const { Meta } = Card;

const categoryMap = {
    "Shirt": "Футболка",
    "Pants": "Штаны",
    "Shoes": "Обувь",
    "Socks": "Носки",
    "Glasses": "Очки",
    "Scarf":"Шарф",
    "Shorts":"Шорты",
    "Sweatshirt":"Свитшот",
    "Jacket":"Куртка",
    "Slippers":"Тапки",
    "Hat":"Головной убор"
};

function ProductCard({ product }) {
    return (
        <Link to={`/product/${product.id}`} state={{ product }}>
            <Card
                hoverable
                style={{ width: 240 }}
                cover={product.image_url ? <img alt={product.name} src={`http://localhost:8080${product.image_url}`} style={{ height: 200, objectFit: 'cover' }} /> : null}
            >
                <Meta
                    title={product.name}
                    description={
                        <>
                            Цена: {product.price}$<br />
                            Тип: {categoryMap[product.category] || product.category}<br />
                            Пол: {product.sex === 'MALE' ? 'Мужской' : 'Женский'}
                        </>
                    }
                />
            </Card>
        </Link>
    );
}

export default ProductCard;