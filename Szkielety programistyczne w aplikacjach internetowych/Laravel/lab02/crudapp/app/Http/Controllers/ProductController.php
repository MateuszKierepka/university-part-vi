<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use App\Models\Product;

class ProductController extends Controller
{
    public function index()
    {
        $products = Product::all();
        return $products;
    }
    public function show($id)
    {
        $product = Product::findOrFail($id);
        return $product;
    }
    public function store(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'name' => 'required|regex:/^[a-zA-Z]{1,30}$/i',
            'price' => 'required|numeric|min:0|max:1000000',
        ]);
        if ($validator->fails()) {
            return ($validator->errors());
        }
        $product = new Product();
        $product->name = $request->input('name');
        $product->price = $request->input('price');
        $product->save();
        return $product;
    }
    public function update(Request $request, $id)
    {
        $product = Product::find($id);
        if ($product!==null){
            $product->name = $request->input('name');
            $product->price = $request->input('price');
            $product->save();
            return $product;
        }
        else 
            return response()->json(['message' => "Nie istnieje w bazie obiekt o podanym id=$id"], 404);
    }
    public function destroy(string $id)
    { 
        $product = Product::find($id);
        if ($product!==null){
            $product->delete();
            return $product;
        }
        else 
            return "Nie istnieje w bazie obiekt o podanym id=$id";
    }
}
