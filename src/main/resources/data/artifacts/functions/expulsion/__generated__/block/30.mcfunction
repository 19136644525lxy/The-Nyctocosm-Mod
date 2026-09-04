#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score a.temp.UUID0 a.value run data get entity @s UUID[0]
execute store result score a.temp.UUID1 a.value run data get entity @s UUID[1]
execute store result score a.temp.UUID2 a.value run data get entity @s UUID[2]
execute store result score a.temp.UUID3 a.value run data get entity @s UUID[3]
execute if score a.temp.UUID0 a.value = a.temp2.UUID0 a.value if score a.temp.UUID1 a.value = a.temp2.UUID1 a.value if score a.temp.UUID2 a.value = a.temp2.UUID2 a.value if score a.temp.UUID3 a.value = a.temp2.UUID3 a.value run function artifacts:expulsion/__generated__/block/31