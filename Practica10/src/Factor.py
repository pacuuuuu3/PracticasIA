# Clase que representa a una variable
class Variable:
    # nombre - El nombre de la variable
    # valor - Valor de la variable
    def __init__(self, nombre, valor):
        self.nombre = nombre
        self.valor = valor
    
    # Regresa una cadena que representa a la variable
    def __str__(self):
        return self.nombre + ' = ' + str(self.valor)

    # Regresa el nombre de la Variable
    def get_nombre(self):
        return self.nombre

# Clase que representa a un Factor
class Factor:
    # variables - Diccionario de valores donde las llaves son conjuntos de 
    # Variables
    def __init__(self, valores):
        self.valores = valores

    # Regresa una cadena que representa al Factor
    def __str__(self):
        cadena = '' # Cadena a regresar
        for valor, proba in self.valores.items():
            cadena += 'P('
            for variable in valor:
                cadena += str(variable) + ', '
            cadena = cadena[:-2]
            cadena += ') = ' + str(proba) + '\n'
        return cadena
    
    # Multiplica dos factores
    # otro_factor - El otro factor a multiplicar
    def multiplica(self, otro_factor):
        valores_nuevos = {} # Los valores del nuevo factor
        for valor1, proba1 in self.valores.items():
            for valor2, proba2 in otro_factor.valores.items():
                l = []
                for variable1 in valor1:
                    l.append(variable1)
                for variable2 in valor2:
                    l.append(variable2)
                f = frozenset(l) # Conjunto de variables
                valores_nuevos[f] = proba1 * proba2
        return Factor(valores_nuevos)
    
    # Crea un nuevo factor fijando una variable
    # variable - La variable a fijar
    def reduce(self, variable):
        valores_nuevos = {} # Los valores del nuevo factor
        for valor, proba in self.valores.items():
            if variable in valor:
                l = list(valor) # Copia del conjunto
                l.remove(variable)
                f = frozenset(l) # Llave
                if f in valores_nuevos:
                    valores_nuevos[f] += proba
                else:
                    valores_nuevos[f] = proba
        return Factor(valores_nuevos)

    # Normaliza un factor 
    def normaliza(self):
        suma = 0.0
        for valor, proba in self.valores.items():
            suma += proba
        for valor, proba in self.valores.items():
            proba /= suma
            self.valores[valor] = proba

    # Marginaliza una variable del factor
    # nombre - Nombre de la variable a marginalizar
    def marginaliza(self, nombre):
        valores_nuevos = {} # Los valores del nuevo factor
        for valor, proba in self.valores.items():
            for v in valor:
                if v.get_nombre() == nombre:
                    l = list(valor) # Copia del conjunto de variables
                    l.remove(v)
                    f = frozenset(l) # Llave
                    if f in valores_nuevos:
                        valores_nuevos[f] += proba
                    else:
                        valores_nuevos[f] = proba
                    break # Para no terminar sin sentido el loop
        return Factor(valores_nuevos)

# Prueba de multiplicacion
a0 = Variable('A', 0)
a1 = Variable('A', 1)
b0 = Variable('B', 0)
b1 = Variable('B', 1)
set1 = frozenset([a0])
set2 = frozenset([a1])
set3 = frozenset([b0])
set4 = frozenset([b1])
f1 = Factor({set1: 0.3, set2: 0.7})
f2 = Factor({set3: 0.6, set4: 0.4})
f3 = f1.multiplica(f2)
print(f1)
print(f2)
print(f3)

# Prueba de reduccion
set5 = frozenset([a0, b0])
set6 = frozenset([a0, b1])
set7 = frozenset([a1, b0])
set8 = frozenset([a1, b1])
f4 = Factor({set5: 0.18, set6: 0.12, set7: 0.42, set8: 0.28})
f5 = f4.reduce(a0)
print(f4)
print(f5)

# Prueba de normalizacion
f5.normaliza()
print(f5)

# Prueba de marginalizacion
f6 = f4.marginaliza('B')
print(f6)
