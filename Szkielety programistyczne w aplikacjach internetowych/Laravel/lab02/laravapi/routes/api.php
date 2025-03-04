<?php

use App\Models\Product;
use App\Http\Controllers\ProductController;
use App\Http\Controllers\AuthController;
use Illuminate\Support\Facades\Route;
use Laravel\Sanctum\Http\Middleware\EnsureFrontendRequestsAreStateful;
use Illuminate\Routing\Middleware\ThrottleRequests;
use Illuminate\Routing\Middleware\SubstituteBindings;

// Route::get('/products', function(){
//     return Product::all();
// });
// Route::get('/products', [ProductController::class, 'index']);

// // Route::post('/products', function(){
// //     return Product::create([
// //         'name' => 'Produkt pierwszy',
// //         'description' => 'To jest przykladowy produkt',
// //         'price' => 235
// //     ]);
// // });
// Route::post('/products', [ProductController::class, 'store']);

// Route::get('/products/{id}', [ProductController::class, 'show']);

// Route::resource('products', ProductController::class);

//Trasy publiczne
Route::get('/products', [ProductController::class, 'index']);
Route::get('/products/{id}', [ProductController::class, 'show']);
Route::get('/products/search/{name}', [ProductController::class, 'search']);
Route::post('/register', [AuthController::class, 'register']);
Route::post('/login', [AuthController::class, 'login']);

//Trasy chronione
Route::middleware([
    'auth:sanctum',
    ThrottleRequests::class.':api',
    SubstituteBindings::class,
])->group(function () {
    Route::post('/products', [ProductController::class, 'store']);
    Route::patch('/products/{id}', [ProductController::class, 'update']);
    Route::delete('/products/{id}', [ProductController::class, 'destroy']);
    Route::post('/logout', [AuthController::class, 'logout']);
});