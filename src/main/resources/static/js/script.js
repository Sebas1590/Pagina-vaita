document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('contactForm');

    form.addEventListener('submit', function (event) {
        event.preventDefault(); // Evita envío automático

        if (form.checkValidity()) {
            const nombre = document.getElementById('nombre').value.trim();
            const edad = parseInt(document.getElementById('edad').value);
            const genero = document.getElementById('genero').value;
            const telefono = document.getElementById('telefono').value.trim();

            const nombreValido = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/.test(nombre);
            const telefonoValido = /^\d{9}$/.test(telefono);

            if (!nombreValido) {
                alert("El nombre solo debe contener letras y espacios.");
                return;
            }

            if (!telefonoValido) {
                alert("El número de teléfono debe tener exactamente 9 dígitos.");
                return;
            }

            if (edad < 1 || edad > 120 || isNaN(edad)) {
                alert("La edad debe estar entre 1 y 120.");
                return;
            }

            if (genero === "") {
                alert("Por favor selecciona un género.");
                return;
            }

            // Si todo es válido, muestra el modal de confirmación
            const modal = new bootstrap.Modal(document.getElementById('modalConfirmacion'));
            modal.show();

        } else {
            form.reportValidity(); // Muestra errores del navegador
        }
    });
});

