import React from 'react';
import { Layout } from 'antd';

// Стили для контейнера AppContent
const contentStyle = {
    color: 'white',
    backgroundColor: 'rgb(35,35,35)', // Темный фон из вашего скриншота
    minHeight: 'calc(86vh)',

};

// Стили для сетки 3x2
const gridContainerStyle = {
    width: '90%',
    height: '90%',
    margin: '0 auto', // Центрируем контейнер по горизонтали
    display: 'grid',
    gridTemplateColumns: 'repeat(3, 1fr)', // 3 колонки равной ширины
    gap: '25px', // Расстояние между квадратами
    justifyContent: 'center', // ← Центрируем всю сетку по горизонтали
    justifyItems: 'center',   // ← Центрируем КАЖДЫЙ элемент внутри своей ячейки
};

// Стили для каждого квадратного элемента
const itemStyle = {
    marginTop: '7%',
    width: '100%',
    height: '90%',
    backgroundColor: '#353535',
    border: '3px solid #DAD8BBFF',
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