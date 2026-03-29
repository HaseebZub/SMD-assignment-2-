# Logic Map Document — XMLArchitectApp (Smart Home)
## SMD Assignment 2 — 22f-3621

---

## Requirement Mapping

### F1: Intent — Navigate from Login to Dashboard with Data Passing

| Requirement ID | Class / File Name | Function / Method | Implementation Description |
|---|---|---|---|
| F1 | `LoginActivity.kt` | `navigateToDashboard(email)` | Creates an Intent and uses `putExtra()` to pass USER_EMAIL, USER_NAME, and LOGIN_TIME to DashboardActivity. No static variables or SharedPreferences used. |
| F1 | `LoginActivity.kt` | `validateInput(email, password)` | Validates user credentials before navigation. Checks for empty fields and minimum password length. |
| F1 | `DashboardActivity.kt` | `receiveIntentData()` | Receives data from LoginActivity using `intent.getStringExtra()` and `intent.getLongExtra()`. Extracts USER_NAME, USER_EMAIL, and LOGIN_TIME. |
| F1 | `activity_login.xml` | Layout | Login screen with TextInputLayout for email/password and a Login button. |
| F1 | `activity_dashboard.xml` | Layout | Dashboard container with welcome header showing received user data. |

---

### F2: Bundle — Transfer Custom Object from RecyclerView to Detail Fragment

| Requirement ID | Class / File Name | Function / Method | Implementation Description |
|---|---|---|---|
| F2 | `Product.kt` | Data Class | Custom data class implementing `Serializable` interface. Contains id, name, category, price, rating, description, and imageResName fields. |
| F2 | `ProductDetailFragment.kt` | `newInstance(product)` | Companion object factory method that creates a Bundle, puts the Product object using `putSerializable(ARG_PRODUCT, product)`, and sets it as fragment arguments. |
| F2 | `ProductDetailFragment.kt` | `onCreate()` | Retrieves Product object from Bundle using `arguments?.getSerializable(ARG_PRODUCT)`. |
| F2 | `DashboardActivity.kt` | `showProductDetail(product)` | Called when a RecyclerView item is clicked. Creates ProductDetailFragment via `newInstance(product)` and performs fragment transaction. |
| F2 | `ProductAdapter.kt` | `onBindViewHolder()` | Sets click listener on each item that invokes the `onItemClick` callback with the Product object. |

---

### F3: RecyclerView — Vertical List with Custom Adapter and ViewHolder

| Requirement ID | Class / File Name | Function / Method | Implementation Description |
|---|---|---|---|
| F3 | `ProductAdapter.kt` | Class | Custom RecyclerView.Adapter implementing the Adapter pattern with inner ProductViewHolder class. |
| F3 | `ProductAdapter.ProductViewHolder` | Inner Class | Custom ViewHolder that holds references to product_image, product_name, product_category, product_price, product_rating, and btn_view_details views. |
| F3 | `ProductAdapter.kt` | `onCreateViewHolder()` | Inflates `item_product_row.xml` layout and creates a new ProductViewHolder instance. |
| F3 | `ProductAdapter.kt` | `onBindViewHolder()` | Binds Product data to ViewHolder views. Sets name, category, price, rating, image, and click listeners. |
| F3 | `ProductAdapter.kt` | `getItemCount()` | Returns the size of the filtered product list. |
| F3 | `ProductListFragment.kt` | `setupRecyclerView()` | Configures RecyclerView with LinearLayoutManager, creates ProductAdapter with sample data, and attaches adapter to RecyclerView. |
| F3 | `item_product_row.xml` | Layout | Custom row layout using MaterialCardView with product image, name, category, price, rating, and a "View Details" button. |
| F3 | `Product.kt` | `getSampleProducts()` | Companion object function returning a list of 10 sample smart home products for the RecyclerView. |

---

### F4: Fragment Transactions — Switch Between Fragments Without Restarting Activity

| Requirement ID | Class / File Name | Function / Method | Implementation Description |
|---|---|---|---|
| F4 | `DashboardActivity.kt` | `setupBottomNavigation()` | Configures BottomNavigationView with `setOnItemSelectedListener`. Each menu item triggers a different fragment load (Home, Products, Profile). |
| F4 | `DashboardActivity.kt` | `loadFragment(fragment)` | Uses `supportFragmentManager.beginTransaction().replace().addToBackStack().commit()` to swap fragments in the FrameLayout container without restarting the activity. |
| F4 | `DashboardActivity.kt` | `showProductDetail(product)` | Performs fragment transaction to replace current fragment with ProductDetailFragment. Adds to back stack for navigation. |
| F4 | `DashboardActivity.kt` | `onBackPressed()` | Handles back navigation by popping the fragment back stack instead of finishing the activity. |
| F4 | `activity_dashboard.xml` | Layout | Contains `FrameLayout` (id: fragment_container) as the fragment host and `BottomNavigationView` for switching. |
| F4 | `HomeFragment.kt` | `newInstance()` | Factory method creating fragment with Bundle arguments. |
| F4 | `ProductListFragment.kt` | `newInstance()` | Factory method creating fragment with Bundle arguments. |
| F4 | `ProductDetailFragment.kt` | `newInstance()` | Factory method creating fragment with Bundle arguments. |

---

### F5: Search / Filter — RecyclerView Item Filtering

| Requirement ID | Class / File Name | Function / Method | Implementation Description |
|---|---|---|---|
| F5 | `ProductListFragment.kt` | `setupSearch()` | Attaches a TextWatcher to the search EditText. On every text change, calls `productAdapter.filter(query)` to filter the RecyclerView items in real-time. |
| F5 | `ProductAdapter.kt` | `filter(query)` | Filters the product list by comparing the query against product name and category (case-insensitive). Updates the adapter's data and calls `notifyDataSetChanged()`. Uses a separate `fullList` to maintain the original unfiltered data. |
| F5 | `fragment_product_list.xml` | Layout | Contains an EditText (id: et_search) with search icon and hint text "Search products..." above the RecyclerView. |

---

## Global Constraints Compliance

| Constraint | Compliance | Evidence |
|---|---|---|
| Data passing via Intent Extras & Bundles only | ✅ Compliant | LoginActivity uses `putExtra()`, all Fragments use `Bundle` arguments via `newInstance()` factory methods. No static variables or SharedPreferences. |
| Modular UI using Fragments | ✅ Compliant | DashboardActivity acts only as container with FrameLayout. All UI content is in HomeFragment, ProductListFragment, and ProductDetailFragment. |
| All lists via RecyclerView + custom Adapter + ViewHolder | ✅ Compliant | ProductAdapter extends RecyclerView.Adapter with inner ProductViewHolder class. |
| Clean Architecture folders | ✅ Compliant | Package structure: `activities/`, `fragments/`, `adapters/`, `models/` |

---

## Project Structure

```
com.example.xmlarchitectapp/
├── activities/
│   ├── LoginActivity.kt          (F1)
│   └── DashboardActivity.kt      (F1, F4)
├── fragments/
│   ├── HomeFragment.kt            (F4)
│   ├── ProductListFragment.kt     (F3, F4, F5)
│   └── ProductDetailFragment.kt   (F2, F4)
├── adapters/
│   └── ProductAdapter.kt          (F3, F5)
├── models/
│   └── Product.kt                 (F2, F3)
└── MainActivity.kt                (Original - Assignment 1)
```

---

## Navigation Flow

```
LoginActivity ──(Intent Extras)──> DashboardActivity
                                      │
                     BottomNavigationView (F4)
                    ┌────────┼───────────┐
                    ▼        ▼           ▼
              HomeFragment  ProductList  Profile
                            Fragment
                               │
                         (Bundle + F2)
                               ▼
                        ProductDetail
                          Fragment
```

---

**Student ID:** 22f-3621
**GitHub Repository:** https://github.com/HaseebZub/SMD-assignment-2-.git
