package com.moronlu18.invoice

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.signup.utils.Locator
import com.moronlu18.InvoiceDavid.entity.InvoiceDao
import com.moronlu18.InvoiceDavid.entity.InvoiceId
import com.moronlu18.InvoiceDavid.entity.InvoiceStatus
import com.moronlu18.InvoiceDavid.entity.LineaItem
import com.moronlu18.InvoiceDavid.entity.LineaItemDao
import com.moronlu18.customer.entity.Customer
import com.moronlu18.customer.entity.CustomerDao
import com.moronlu18.customer.entity.CustomerId
import com.moronlu18.customer.repository.CustomerRepository
import com.moronlu18.invoice.converter.CustomerEmailTypeConverter
import com.moronlu18.invoice.converter.CustomerIDTypeConverter
import com.moronlu18.invoice.converter.InvoiceIdTypeConverter
import com.moronlu18.invoice.converter.InvoiceInstantLongConverter
import com.moronlu18.invoice.converter.InvoiceStatusConverter
import com.moronlu18.invoice.converter.ItemIdTypeConverter
import com.moronlu18.invoice.converter.ItemTaxableBoolConverter
import com.moronlu18.invoice.converter.ItemTypeConverter
import com.moronlu18.invoice.converter.TaskCustomerIdConverter
import com.moronlu18.invoice.converter.TaskIdConverter
import com.moronlu18.invoice.converter.TaskStatusConverter
import com.moronlu18.invoice.converter.TaskTypeConverter
import com.moronlu18.invoice.entity.Invoice
import com.moronlu18.invoice.ui.firebase.Email
import com.moronlu18.invoice.ui.utils.calendar.CalendarInvoice
import com.moronlu18.item.entity.ItemDao
import com.moronlu18.item.entity.ItemId
import com.moronlu18.item.entity.item
import com.moronlu18.item.entity.itemType
import com.moronlu18.task.entity.Task
import com.moronlu18.task.entity.TaskDao
import com.moronlu18.task.entity.TaskId
import com.moronlu18.task.entity.TaskStatus
import com.moronlu18.task.entity.TaskType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.time.Instant

@Database(
    entities = [Invoice::class, LineaItem::class, item::class, Task::class, Customer::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(
    InvoiceIdTypeConverter::class,
    InvoiceInstantLongConverter::class,
    InvoiceStatusConverter::class,
    ItemTypeConverter::class,
    ItemIdTypeConverter::class,
    ItemTaxableBoolConverter::class,
    CustomerIDTypeConverter::class,
    CustomerEmailTypeConverter::class,
    TaskCustomerIdConverter::class,
    TaskIdConverter::class,
    TaskStatusConverter::class,
    TaskTypeConverter::class

)
abstract class InvoiceDatabase : RoomDatabase() {

    abstract fun invoiceDao(): InvoiceDao
    abstract fun lineaItemDao(): LineaItemDao
    abstract fun customerDao(): CustomerDao

    abstract fun taskDao(): TaskDao

    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var INSTANCE: InvoiceDatabase? = null
        fun getInstance(): InvoiceDatabase {
            return INSTANCE ?: synchronized(InvoiceDatabase::class) {
                val instance = buildDatabase()
                INSTANCE = instance
                instance
            }


        }

        private fun buildDatabase(): InvoiceDatabase {
            return Room.databaseBuilder(
                Locator.requiredApplication, InvoiceDatabase::class.java, "Invoice"
            ).fallbackToDestructiveMigration().allowMainThreadQueries()//quitarlo
                .addTypeConverter(InvoiceIdTypeConverter())
                .addTypeConverter(InvoiceInstantLongConverter())
                .addTypeConverter(InvoiceStatusConverter())
                .addTypeConverter(ItemIdTypeConverter())
                .addTypeConverter(ItemTypeConverter())
                .addTypeConverter(ItemTaxableBoolConverter())
                .addTypeConverter(CustomerIDTypeConverter())
                .addTypeConverter(CustomerEmailTypeConverter())
                .addTypeConverter(TaskIdConverter())
                .addTypeConverter(TaskStatusConverter())
                .addTypeConverter(TaskTypeConverter())
                .addTypeConverter(TaskCustomerIdConverter())
                .addCallback(
                    RoomDbInitializer(INSTANCE)
                ).build()
        }
    }

    class RoomDbInitializer(val instance: InvoiceDatabase?) : RoomDatabase.Callback() {

        private val applicationScope = CoroutineScope(SupervisorJob())

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            applicationScope.launch(Dispatchers.IO) {
                populateDatabase()
            }
        }

        private fun populateDatabase() {
            populateCustomer()
            populateItem()
            populateInvoice()
            populateTask()
        }



        private fun populateInvoice() {
            var listaItem =mutableListOf<LineaItem>(
                LineaItem(
                    1, 1, 2, 1.5, 45.5
                )
            )

            //.let ejecuta el código si no es nulo
            getInstance().let { invoiceDatabase ->
                invoiceDatabase.invoiceDao().insert(
                    Invoice(
                        InvoiceId(1),
                        CustomerId(1),
                        SetFecha("2021-01-20"),
                        SetFecha("2021-01-20"),
                        InvoiceStatus.Pending,
                        "2021DDD"
                    )
                )
                listaItem.forEach {
                    invoiceDatabase.lineaItemDao().insert(it)
                }

            }

        }
        private fun populateCustomer() {


            //.let ejecuta el código si no es nulo
            getInstance().let { invoiceDatabase ->
                invoiceDatabase.customerDao().insert(
                    Customer(
                        CustomerId(2),
                        "Paco",
                        "Urquiza Falle",
                        Email("carnetaadspjf@gmail.com"),
                        "6846556414",
                        "Málaga",
                        "Calle Leonora n46"
                    )

                )
                invoiceDatabase.customerDao().insert(
                    Customer(
                        CustomerId(1),
                        "Alex",
                        "Carnero Tapia",
                        Email("carnetaadspjf@gmail.com"),
                        "65478966",
                        "Málaga",
                        "Calle Leonora n46"
                    )

                )
            }

        }
        private fun populateTask() {
            getInstance().taskDao().insert(
                Task(
                    TaskId(1),
                    CustomerRepository.getCliente(2),
                    "Crear Base de Datos",
                    "Funciona la base por fin",
                    TaskType.visitor,
                    TaskStatus.overdue,
                    "27/02/2024",
                    CalendarInvoice.getCurrentDate()
                    ))
            getInstance().taskDao().insert(
                Task(
                    TaskId(2),
                    CustomerRepository.getCliente(1),
                    "Mejorar base de datos",
                    "Regular",
                    TaskType.private,
                    TaskStatus.modified,
                    "28/02/2024",
                    CalendarInvoice.getCurrentDate()
                ))
        }
        private fun SetFecha(fecha: String): Instant {
            val dateString = fecha + "T00:00:00Z"
            //val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
            //val localDateTime = LocalDateTime.parse(dateString, formatter)
            val instant = Instant.parse(dateString)
            //return localDateTime.toInstant(ZoneOffset.MAX)
            return instant
        }


        private fun populateItem() {
            getInstance().itemDao().insert(
                item(ItemId(1),"zanahoria",4.56,itemType.PRODUCT, "es mediana", false, 0.02),

            )
        }
    }
}