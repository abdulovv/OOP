// src/components/ProductCard.jsx
import React from 'react';
import { Card } from 'antd';
import { Link } from 'react-router-dom';

const { Meta } = Card;

function ProductCard({ product }) {
    return (
        <Link to={`/product/${product.id}`}>
            <Card
                hoverable
                style={{
                    width: 240,
                    marginBottom: 20,
                    border: '1px solid #e0e0e0',
                    maxWidth: 280, // Добавили максимальную ширину
                }}
                cover={<img alt={product.name} src={product.image} style={{ height: 200, objectFit: 'cover' }} />}
            >
                <Meta
                    title={product.name}
                    description={`Цена: $${product.price}`}
                />
            </Card>
        </Link>
    );
}

export default ProductCard;