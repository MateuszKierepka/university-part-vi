<?php

use App\Http\Controllers\ProfileController;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\CommentController;

Route::get('/', function () {
    return view('welcome');
});

Route::get('/comments', [CommentController::class, 'index'])->name('comments');
Route::get('/create', [CommentController::class, 'create'])->name('create');
Route::post('/create', [CommentController::class, 'store'])->name('store');

Route::get('/delete/{id}', [CommentController::class, 'destroy'])->name('delete');

Route::get('/edit/{id}', [CommentController::class,'edit'])->name('edit');
// Route::put('/update/{id}', [CommentController::class,'update'])->name('update');
Route::post('/update/{id}', [CommentController::class,'update'])->name('update');

Route::get('/dashboard', function () {
    return view('dashboard');
})->middleware(['auth', 'verified'])->name('dashboard');

Route::middleware('auth')->group(function () {
    Route::get('/profile', [ProfileController::class, 'edit'])->name('profile.edit');
    Route::patch('/profile', [ProfileController::class, 'update'])->name('profile.update');
    Route::delete('/profile', [ProfileController::class, 'destroy'])->name('profile.destroy');
});

require __DIR__.'/auth.php';
