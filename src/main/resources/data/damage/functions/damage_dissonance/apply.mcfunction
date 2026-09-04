#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score @s admg run data get storage admg:data damage.amount 10
execute in overworld run function damage:damage_dissonance/bin_decomposition
tag @e remove damager
tag @a remove damager