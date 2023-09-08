package com.example.test


//class CustomerRepositoryTest {
//
//    @get:Rule
//    val instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    @Mock
//    private lateinit var database: CustomerDatabase
//
//    @Mock
//    private lateinit var apiCustomerObj: ApiCustomerObj
//
//    private lateinit var repository: CustomerRepository
//
//    private val testDispatcher = TestScope()
//
//    private val testCoroutineScope = TestScope()
//
//    @Before
//    fun setUp() {
//        MockitoAnnotations.openMocks(this)
//        repository = CustomerRepository(database)
//    }
//
//    @Test
//    fun testRefreshCustomers() = runBlocking {
//        // Arrange
//        val apiCustomers = listOf(
//            ApiCustomer(
//                id = 2,
//                email = "customer2@example.com",
//                name = "Customer Two",
//                phoneNr = "987-654-3210",
//                )
//
//
//        )
//        val expectedCustomers = apiCustomers.map { it.asDatabaseCustomer() }
//        Mockito.`when`(apiCustomerObj.retrofitService.getAllCustomers().await()).thenReturn(
//            ApiCustomerContainer(apiCustomers)
//        )
//
//        // Act
//        repository.refreshCustomers()
//
//        // Assert
//        // Verify that the database insertAll method was called with the expected customers
//        Mockito.verify(database.customerDatabaseDao).insertAll(*expectedCustomers.toTypedArray())
//    }
//
//    @Test
//    fun testCustomersLiveData() {
//        // Arrange
//        val mockObserver = Mockito.mock(Observer::class.java) as Observer<List<Customer>>
//        repository.customers.observeForever(mockObserver)
//
//        // Act (In this case, you would need to update the LiveData with some data)
//
//        // Assert (Verify that the LiveData observer receives the expected data)
//        // For example:
//        // Mockito.verify(mockObserver).onChanged(expectedData)
//
//        // Don't forget to remove the observer to avoid memory leaks
//        repository.customers.removeObserver(mockObserver)
//    }
//
//
//
//    fun createApiCustomers(): List<Customer> {
//        val apiCustomer1 = Customer(
//            id = 1,
//            email = "customer1@example.com",
//            name = "Customer One",
//            department = "sales".asDomainDepartment(),
//            education = "Bachelor's Degree",
//            externType = "Type A",
//            phoneNr = "123-456-7890",
//            backupContactId = 101
//        )
//
//        val apiCustomer2 = Customer(
//            id = 2,
//            email = "customer2@example.com",
//            name = "Customer Two",
//            department = "sales".asDomainDepartment(),
//            education = "Master's Degree",
//            externType = "Type B",
//            phoneNr = "987-654-3210",
//            backupContactId = 102
//        )
//
//        // Add more API customers as needed
//
//        return listOf(apiCustomer1, apiCustomer2)
//    }
//}