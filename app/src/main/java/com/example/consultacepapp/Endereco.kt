package com.example.consultacepapp

data class Endereco(
    val cep : String,
    val logradouro : String,
    val complemento : String,
    val bairro : String,
    val localidade : String,
    val uf : String,
    val ibge : String,
    val gia : String,
    val ddd : String,
    val siafi : String
) {
    override fun toString(): String {
        return "CEP: $cep\nLogradouro: $logradouro\nComplemento: $complemento\nBairro: $bairro\nCidade: $localidade\nUF: $uf\nIBGE: $ibge\n" +
                "GIA: $gia\nDDD: $ddd\nSIAFI: $siafi"
    }
}
