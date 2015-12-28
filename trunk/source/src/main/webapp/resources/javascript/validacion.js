function validarCantidadAgregar() {
   
    if (isNaN(document.formAgregar.cantidadAgregar.value) || document.formAgregar.cantidadAgregar.value < 1) {
        alert('Por favor, ingrese una cantidad numérica superior a cero.');
        return false;
    }
   
    return true;
}
function validarCantidadAgregarStock() {
    
    if (isNaN(document.formAgregar.cantidadAgregar.value) || document.formAgregar.cantidadAgregar.value < 1) {
        alert('Por favor, ingrese una cantidad numérica superior a cero.');
        return false;
    }
    var cantidad= parseInt(document.formAgregar.cantidadAgregar.value);
    var cantidadMaxima = parseInt(document.formAgregar.cantidadMaxima.value);

    if(cantidadMaxima < cantidad){
        alert('Por favor, ingrese una cantidad numérica que no supere el maximo indicado de '+cantidadMaxima+' unidades ');
        return false;
        
    }

    return true;
}
function validarEditarDetalleStock() {
    if (isNaN(document.formEditar.cantidadEditar.value) || document.formEditar.cantidadEditar.value < 1) {
        alert('Por favor, ingrese una cantidad numérica superior a cero.');
        return false;
    }
    if (isNaN(document.formEditar.precioEditar.value) || document.precioEditar.cantidadEditar.value < 1) {
        alert('Por favor, ingrese una precio válido superior a cero.');
        return false;
    }
    var cantidad= parseInt(document.formEditar.cantidadEditar.value);
    var cantidadMaxima = parseInt(document.formEditar.stock.value);
    if (cantidadMaxima < cantidad){
        alert('Por favor, ingrese una cantidad numérica que no supere el stock disponible de '+cantidadMaxima);
        return false;
    }
    return true;
}


function validarEditarDetalle() {
    if (isNaN(document.formEditar.cantidadEditar.value) || document.formEditar.cantidadEditar.value < 1) {
        alert('Por favor, ingrese una cantidad numérica superior a cero.');
        return false;
    }
    if (isNaN(document.formEditar.precioEditar.value) || document.precioEditar.cantidadEditar.value < 1) {
        alert('Por favor, ingrese una precio válido superior a cero.');
        return false;
    }
    return true;
}


