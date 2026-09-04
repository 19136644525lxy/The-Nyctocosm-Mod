# /data modify storage admg:data damage set value {executor:'"goblin"',amount:2.5}
#execute in overworld run data modify entity ba5a5e65-e90c-4434-9e98-9e4c5a7e7347 CustomName set from storage admg:data damage.executor
execute store result score @s admg run data get storage admg:data damage.amount 10
execute in overworld run function damage:damage/bin_decomposition
tag @e remove damager