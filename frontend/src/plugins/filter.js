export function formatDateTime(dateTime) {
    const options = { 
        year: 'numeric', 
        month: '2-digit', 
        day: '2-digit',
        hour: '2-digit', 
        minute: '2-digit' 
    }
    return new Date(dateTime).toLocaleString('pt-BR', options)
}

export function formatMoney(value) {
    return Number(value).toLocaleString('pt-BR', {
        style: 'currency',
        currency: 'BRL'
    })
}

