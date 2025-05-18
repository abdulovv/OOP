import React from 'react';
import { Layout } from 'antd';

// Стили для контейнера AppContent
const contentStyle = {
    color: 'white',
    backgroundColor: '#1a1a1a', // Темный фон из вашего скриншота
    minHeight: 'calc(86vh)',

};

// Стили для сетки 3x2
const gridContainerStyle = {
    width: '95%',
    height: '85%',
    maxWidth: '95%', // Ограничиваем максимальную ширину контейнера
    maxHeight: '85%',
    margin: '0 auto', // Центрируем контейнер по горизонтали
    display: 'grid',
    gridTemplateColumns: 'repeat(3, 1fr)', // 3 колонки равной ширины
    gap: '25px', // Расстояние между квадратами
};

// Стили для каждого квадратного элемента
const itemStyle = {
    marginTop: '24px',
    width: '100%',
    height: '250px',
    backgroundColor: '#3a3a3a',
    border: '3px solid white',
    position: 'relative', // Для центрирования контента внутри
    borderRadius: '8px',
};

// Стили для текста/изображения внутри квадрата
const innerItemStyle = {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    fontSize: '20px', // Размер текста
    fontWeight: 'bold',
};

export default function AppContent() {
    return (
        <Layout.Content style={contentStyle}>
            {/* Обертка для сетки */}
            <div style={gridContainerStyle}>
                {[...Array(6)].map((_, index) => (
                    <div key={index} style={itemStyle}>
                        <div style={innerItemStyle}>
                            Фотография {index + 1}
                        </div>
                    </div>
                ))}
            </div>
        </Layout.Content>
    );
}