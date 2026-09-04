#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score HurtTime value run data get entity @s HurtTime
execute unless score HurtTime value matches 1.. run tag @s add em.impact.flame