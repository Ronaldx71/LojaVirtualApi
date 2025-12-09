# 🛒 LojaVirtualApi
Aplicativo Android desenvolvido em **Kotlin + Jetpack Compose**, consumindo a API pública **DummyJSON**.  
O app demonstra navegação, integração com Retrofit, arquitetura modular e trabalho em equipe usando **Git Flow**.

---

## 📱 Funcionalidades do Aplicativo

### ✔️ **1. Tela Principal (Home)**
- Menu com 4 categorias:
    - **Produtos**
    - **Carrinhos**
    - **Usuários**
    - **Postagens**

### ✔️ **2. Listagem e Detalhes**
Cada módulo possui:
- Tela de listagem (`ListScreen`)
- Tela de detalhes (`DetailScreen`)
- Consumo real da API DummyJSON via Retrofit

**Módulos disponíveis:**
| Módulo | Tela de Lista | Tela de Detalhes |
|--------|----------------|-------------------|
| Produtos | `ProductListScreen` | `ProductDetailScreen` |
| Carrinhos | `CartListScreen` | `CartDetailScreen` |
| Usuários | `UsersListScreen` | `UserDetailScreen` |
| Postagens | `PostsListScreen` | `PostDetailScreen` |

---

## 🧱 Arquitetura do Projeto

```bash
app/
 ├── api/
 │    ├── DummyApi.kt
 │    ├── RetrofitInstance.kt
 │
 ├── model/
 │    ├── Product.kt
 │    ├── ProductResponse.kt
 │    ├── Cart.kt
 │    ├── CartResponse.kt
 │    ├── User.kt
 │    ├── UsersResponse.kt
 │    ├── Post.kt
 │    ├── PostsResponse.kt
 │
 ├── navigation/
 │    ├── AppNav.kt
 │
 ├── ui/
 │    ├── home/
 │    │     └── HomeScreen.kt
 │    ├── products/
 │    │     ├── ProductListScreen.kt
 │    │     └── ProductDetailScreen.kt
 │    ├── carts/
 │    │     ├── CartListScreen.kt
 │    │     └── CartDetailScreen.kt
 │    ├── users/
 │    │     ├── UserListScreen.kt
 │    │     └── UserDetailScreen.kt
 │    └── posts/
 │          ├── PostsListScreen.kt
 │          └── PostDetailScreen.kt
