// src/pages/CatalogPage.jsx
import React, { useState, useEffect } from 'react';
import { Typography, Button, Select, Input } from 'antd';
import ProductCard from '../components/ProductCard';
import axios from 'axios';

const { Option } = Select;
const { Search } = Input;

const categoryMap = {
    "Shirt": "Футболка",
    "Pants": "Штаны",
    "Shoes": "Обувь",
    "Socks": "Носки",
    "Glasses": "Очки",
    "Scarf": "Шарф",
    "Shorts": "Шорты",
    "Sweatshirt": "Свитшот",
    "Jacket": "Куртка",
    "Slippers": "Тапки",
    "Hat": "Головной убор"
};
const categoryOptions = Object.entries(categoryMap).map(([key, value]) => ({ value: key, label: value }));

const priceSortOptions = [
    { value: null, label: 'По умолчанию' },
    { value: 'asc', label: 'По возрастанию цены' },
    { value: 'desc', label: 'По убыванию цены' },
];

function CatalogPage() {
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [selectedGender, setSelectedGender] = useState(null);
    const [selectedCategory, setSelectedCategory] = useState(null);
    const [sortOrder, setSortOrder] = useState(null);

    useEffect(() => {
        axios.get('http://localhost:8080/api/clothes/')
            .then(response => {
                setProducts(response.data);
                setLoading(false);
            })
            .catch(error => {
                setError(error);
                setLoading(false);
                console.error('Ошибка при загрузке товаров:', error);
            });
    }, []);

    if (loading) {
        return <div>Загрузка товаров...</div>;
    }

    if (error) {
        return <div>Ошибка при загрузке товаров!</div>;
    }

    const rows = [];
    for (let i = 0; i < products.length; i += 3) {
        rows.push(products.slice(i, i + 3));
    }

    const genderFilters = ["Мужское", "Женское", "Не выбрано"];

    const handleGenderFilterClick = (gender) => {
        setSelectedGender(gender === selectedGender ? null : gender);
        // Здесь позже добавим логику фильтрации товаров
    };

    const handleCategoryChange = (value) => {
        setSelectedCategory(value);
        // Здесь позже добавим логику фильтрации по категории
    };

    const handlePriceSortChange = (value) => {
        setSortOrder(value);
        // Здесь позже добавим логику сортировки по цене
    };

    return (
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
            <div style={{ marginBottom: 16, width: '100%', maxWidth: 1200, display: 'flex', justifyContent: 'center' }}>
                <Search placeholder="Поиск товаров" onSearch={value => console.log(value)} style={{ width: '30%', minWidth: '200px' }} />
            </div>

            <div style={{ marginBottom: 16, display: 'flex', gap: 16, alignItems: 'center', justifyContent: 'center' }}>
                <div>
                    {genderFilters.map(gender => (
                        <Button
                            key={gender}
                            style={{
                                marginRight: 8,
                                backgroundColor: selectedGender === gender ? 'black' : '',
                                color: selectedGender === gender ? 'white' : 'inherit',
                            }}
                            onClick={() => handleGenderFilterClick(gender)}
                        >
                            {gender}
                        </Button>
                    ))}
                </div>
                {/*
                <div>
                    {filterTypes.map(type => (
                        <Button key={type} style={{ marginRight: 8 }}>{type}</Button>
                    ))}
                </div>
                */}

                <Select
                    placeholder="Категория"
                    style={{ width: 150 }}
                    onChange={handleCategoryChange}
                    defaultValue={null}
                >
                    <Option value={null}>Все категории</Option>
                    {categoryOptions.map(option => (
                        <Option key={option.value} value={option.value}>{option.label}</Option>
                    ))}
                </Select>

                <Select
                    placeholder="Сортировка"
                    style={{ width: 200 }}
                    onChange={handlePriceSortChange}
                    defaultValue={null}
                >
                    {priceSortOptions.map(option => (
                        <Option key={option.value} value={option.value}>{option.label}</Option>
                    ))}
                </Select>
            </div>

            <div style={{ width: '100%', maxWidth: 1200, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                {rows.map((row, index) => (
                    <div key={index} style={{ display: 'flex', justifyContent: 'center', marginBottom: 16 }}>
                        {row.map(product => (
                            <div key={product.id} style={{ margin: '0 16px' }}>
                                <ProductCard product={product} />
                            </div>
                        ))}
                    </div>
                ))}
            </div>
        </div>
    );
}

export default CatalogPage;