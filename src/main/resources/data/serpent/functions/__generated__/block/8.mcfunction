#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score tempHurtTime value run data get entity @s HurtTime
execute unless score tempHurtTime value matches 1.. run function serpent:__generated__/block/9