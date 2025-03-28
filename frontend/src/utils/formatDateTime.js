export default function formatDateTime(dateTime) {
    const options = { 
        year: 'numeric', 
        month: '2-digit', 
        day: '2-digit',
        hour: '2-digit', 
        minute: '2-digit' 
    }
    return new Date(dateTime).toLocaleString('pt-BR', options)
}
