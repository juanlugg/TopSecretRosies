# Parte articulo (Antonio Salado)

En mi caso mi parte consiste en la parte de articulo, explicare un poco como hice el diseño de los layouts de los tres modulos de itemcreation, itemdetail y itemlist

## itemcreation

En este modulo consiste en añadir un articulo, para este modulo puse varios TextInputLayout que es mejor que el EditText ya que proporciona mas caracteristicas como que facilita la organización del diseño y la interacción con campos de entrada de texto, en estos TextInputLayout se escribiran los atributos de un articulo que son id, nombre, tasa, descripcion. Luego puse un spinner para el tipo de articulo que ya esta inicializado con un ArrayAdapter que obtendra de un arrayof que tendra producto o servicio y que lo mostrara para elegir en el spinner, por defecto esta en producto. Tambien he creado una clase POJO que es item que se usara cuando implementemos la creacion de un articulo y se usara objetos estaticos de prueba que ya lo comentare en el modulo itemlist, puse tambien un checkbox para saber si un articulo es imponible y por ultimo un FloatingactionButton que basicamente añade un articulo cuando hayamos rellenado los TextInputLayout. Puse un color que se pueda ver bien en las letras cuando se escribe en los TextInputLayout, he optado por un diseño sencillo y que se pueda usar facilmente, tambien he usado una fuente que hemos elegido entre todos que es open_sans que lo he usado en los demas modulos y todo estos componentes los puse en un LinearLayout para que cuando se ejecute en la aplicacion se vea bien.

## itemdetail

Para este modulo basicamente es mostrar los detalles de un articulo sin edicion, consiste lo he dividido en dos CardView que dentro tendrian TextView para los atributos de un articulo sin edicion que serian Articulo que muestra los datos basicos de un articulo id, nombre, tasa y caracteristicas que muestra informacion importante de un articulo como de que tipo es, una despcricion en concreto y si es taxable, dentro de los CardView puse un ConstraintLayout que a su vez para posicionar bien los componentes un par de LinearLayout. Elegi esta combinacion de colores por que visualmente esta bien ademas Alex me ayudo como deberia de ser estos colores y tambien use la misma fuente como en el anterior modulo, tambien puse las esquinas de los CardView redondos que a mi personamente me gusta.

## itemlist

Para este modulo muestra basicamente un listado de articulos con sus atributos, lo que hice aqui es poner un RecyclerView con un id, y luego cree otra vista para como se deberia de ver un articulo dentro del RecycleView, elegi el diseño de esta vista en un CardView con un color para que la letra de vea bien tambien con las esquinas redondas, puse dentro un ConstraintLayout y a su vez un par de LinearLayout para posisionar correctamente los componentes que tiene el CardView, dentro del CardView serian TextView que mostraran cada uno de los atrubutos de un articulo. Tambien cree el Adapter de itemlist para proporcionar los atributos de los articulos al RecyclerView. Despues cree varios objetos estaticos de prueba de la clase POJO en el fragment para ver si el RecyclerView los muestra gracias al Adapter. Se muestra bien los articulos con la vista que hice dentro del RecyclerView y se puede hacer scroll para ver mas vistas de articulo. 

## Dificultades que he tenido

He tenido algunas dificultades como que en el itemcreation cuando lo ejecutaba en la aplicacion cuando esta unido en el nav_graph los componentes se me ponian encima de otro a pesar que cuando lo estoy editando el layout para que se ve bien, asi que puse un LinearLayout para posicionar los componentes de manera correcta, me cuesta un poco utilizar el LinearLayout pero poco a poco empiezo a entenderlo, me paso lo mismo en el itemlist pero tambien tuve otra dificultad en ese modulo y es que cuando cree los iten estaticos de prueba para ver si se creaban bien, estaban muy separados cuando estaba haciendo scroll veia el siguiente elemento y asi sucesivamente y estaba mal diseñado, asi estuve haciendo varias pruebas y llege a una solucion que era reducir de tamaño del ConstraintLayout que tenia asignado el layout de itemlist, lo pruebo ya me sale bien, no estan demasiado separados las vistas de los articulos en el RecyclerView. Tambien me costo un poco como hacer el Adapter del itemlist pero poco a poco iba saliendo bien. Me costo un poco al principio como poner en el nav_graph como poner el fragment de cada modulo, pero ya empece a entender como se hace.

## Base de datos con Room

Para esta parte he creado una clase dao para mi modulo un itemDao.kt, que tiene las operaciones de update, insert, delete y select, para la clase que tiene la base de datos de la aplicacion (InvoiceDatabase) puse un populateitem que tendra un articulo de prueba para comprobar si se inserta bien el articulo y se cree la tabla correctamente, he comprobado en el DatabaseInspector si se crea la tabla y si se ven los articulos cuando añado uno y todo funciona lo mismo cuando editas un articulo y borras uno, tambien de que si un articulo esta en una factura no se pueda borrar, he puesto metodos en el viewmodel para hacer las operaciones CRUD de la base de datos, tambien utiliza la clase Resources para el tema de los errores que pueden generar la base de datos con las operaciones

## Notificacion

Para la notificacion en item hice que cuando añades un articulo se te muestra una notificacion con el id del articulo que has añadido hice eso en el ItemCreation, asi cuando el usuario quiera buscar un articulo en concreto pero hay muchos articulos añadidos mediente el id pueda buscar el articulo facilmente que el usuario desea ademas que siempre esta la notificacion con el id del articulo

## Pruebas unitarias

Hice una clase test para hacer pruebas para la clase POJO como por ejemplo que se cree bien el articulo, comparar dos articulos, comprobar si se muestra bien el toString, comprobar si dos articulos son iguales o si no lo son y tambien de comprobar si un articulo es una copia de otro articulo, y tambien otra clase test para comprobar si el id de dos articulos es igual o no

# INVOICE (David Zambrana)
En todos mis layout he implementado un constraintlayout como principal. En ellos me he basado en implementar varios cardview en los cuales distribuyó el contenido. Todo está construido con la misma gama de colores e intentando buscar una similitud en la distribución de los view.
Para el funcionamiento de estos features cree varios fragment en el nav_gragh principal, después implemente varios actions y lo vincule a los botones previamente creados 
## InvoiceCreation
En este layout he implementado varios TextInputLayout en cada cardview, en ellos he cambiado el tipo de letra por una implementada en el proyecto, también he insertado un icono al inicio. En el último cardview puse un botón, el cual cambie el color principal y su tamaño cambiando el height, y un recyclerview en el cual en cada fila hay otro cardview para mostrar los datos y acompañando de un imageview y dispone de su adapter al cual se pasamos un Array con el contenido a mostrar

![](https://i.postimg.cc/L6ZMm5ZB/creation.png)
## InvoiceDetails
En este layout puse varios TextView varios de ellos cumpliendo la funcionalidad de una etiqueta, los cuales puse en negrita, incremente un poco su tamaño, los demas TextView sirven para poner el contenido deseado. También podemos ver un RadioGroup con varios RadioButton, todo este contenido tiene el tipo de letra implementado en el proyecto
Nuevamente he implementado el mismo reciclerview que en InvoiceCreation.

![](https://i.postimg.cc/RhkyW1n1/details.png)
## InvoiceList
En este layout solo podemos ver el RecyclerView, en el layout de cada fila podemos ver un cardview con el contenido junto a una imagen y un textview. Para el recyclerview implemente el adapter necesario para construirlo pasándole una lista de facturas en la cual cada factura es una dataclass con el contenido necesario para los TextView, vinculandose gracias al adapter.

![](https://i.postimg.cc/Kcf54Dtm/list.png)

# Customer (Alex Carnero)
Todo el código de la clase POJO de Customer esta cubierto por una clase de prueba la cual  comprueba el 100% de la clase Customer.
En todos mis layout he implementado un constrainlayout como container principal. He intentado usar la misma gama de colores y fuente de letra que mis compañeros para hacer una aplicación homogénea.

## CustomerCreation
En este layout he utilizado TextInputLayout para recoger la información del cliente , además de esto he añadido un spinner con los prefijos de telefono más usados y un boton el cual sirve para crear el cliente

## CustomerDetail
He usado varion linearlayout para dividir la información del cliente segun de lo que trababa aunque no creo que sea los más optimo para el layout utilizar este tipo de contenedores , pero no se me ocurrio ninguna otra forma.

## CustomerList
Fragmento encargado de listar todos los clientes existentes en la lista de clientes aunque el modelo de datos no este todavia implementado he añadido una lista manualmente para saber como se posionarian los elementos.
## Base de Datos
He tenido que crear dos clases para mi modulo para que funcionaran con la base de datos local de nuestra aplicación la primera seria CustomerDao.kt la cual es la encargada de realizar todas las sentencias de sql con la base de datos y la otra clase seria el CustomerRepository encargada de llamar a la sentencia sql correspondiente de CustomerDao y recoger tanto el los datos devueltos por la sentencia como los errores que se pueden producir con la base de datos
## Notificacion
Yo he creado una notificación a la hora de editar un cliente puesto que al tratarse de una aplicación que registra facturas es importante avisar a los usurios de los cambios de los clientes cuyas facturas emiten para que no se produzca ningun tipo de error.

# TASK (Juan Luis Guerra Gennich) 
## v1
Mi trabajo consistió en la creación y visualización de las tareas. Mi idea fue hacer las tareas como notas comunes, con su fecha y horas en las que esté previsto hacerlas y vinculadas a un cliente.

### TaskCreation
El layout principal, en el que se crearan las tareas y con la intención de implementar la posibilidad de editar dichas tareas. Aqui utilicé varios LinearLayout ordenar los componentes/widgets.
Además se utilizará un Spinner para seleccionar el cliente que ya previamente se debió crear para enlazar esta tarea con un cliente especifico. También hay dos Spinner que servirán para poner la hora.
La fecha de momento es un EditText en formato fecha, pero la intención es con código se pueda abrir un calendario y seleccionar la fecha.

### TaskDetail
En está parte implemente Guideline y Barrier para probar su funcionamiento y hacer un layout sin LinearLayout y con mayor control de tamaños. 
Se podrá visualizar a modo de ejemplo con datos inventados una tarea con sus respectivos datos.

### TaskList
Layout que contiene la lista de todas las tareas creadas, de momento se visualizan tareas de ejemplo. Aqui se implementa el RecycleView para crear cada fragmento de información de las tareas y la función de hacer scroll.
Después de que en TaskCreation se cree una tarea, se añadirá aquí y se podrá visualizar la lista completa de tareas.

## v2
### TaskViewModel y nuevos cambios
He implementado la funcionalidad del viewModel para task y diversos cambios en los layouts y nuevas funcionalidades. También he creado un repositorio estático con un Sigleton para tener tareas de ejemplo. 
Se ha implementado la funcionalidad de editar y borrar una tarea además de vincular estas tareas con un cliente de Customer. También creé un popUp para las fechas donde se mostrará un calentario, 
que puedes elegir cualquier fecha que no haya pasado y en mi caso consideré que la fecha por defecto de creación sea la que esté en la fecha actual cuando se crea, pero no es obligatorio fecha final. 
Además, la creación y edición tiene control de errores como tituló y cliente obligatorios y fecha fin no puede ocurrir antes que fecha inicial/creación. 

## v3
### Preferencias
Además de la preferencia en conjunto del tema de la aplicación, hay preferencia para la ordenación de la lista de Tareas, 

### Notificacion
La lista de tareas notificará cuantas tareas están en estado pendiente, incluyendo las modificadas consideradas como tareas sin completar. La lista llamará a una función en la clase
Notification con los datos necesarios.

### Base de datos
Creación de la tabla Task en la base de datos. Existen dos tareas creadas de ejemplo en InvoiceDatabase. Las funciones de la tabla se hacen en TaskDao, siendo llamadas en TaskRepository, omitiendo la necesidad de tener datos de ejemplo en ProviderTask.

### Test
La clase POJO de Task tiene las pruebas necesarias, además de las pruebas para la clase TaskId y para sus enumeraciones.

### Otros cambios
En la lista, dependiendo del estado (TaskStatus) se mostrará un icono u otro, entre 3 distintos.
Cuando la lista está vacia, mostrará una animación de que no hay datos en la lista, además de que no habrá ninguna notificación.
La ordenación de la lista, por ID (por defecto), y por nombre de cliente. El botón refresh recarga la lista y lo ordena por Id.
El string de task está traducido.
De forma voluntaria he añadido cambio de idioma, pero a veces no funciona correctamente.


