tag @s remove admg.heal
effect clear @s instant_health
attribute @s generic.knockback_resistance modifier add ebfd378c-f0ad-4b56-a448-c105c4078ca4 "no_kb" 100 add
execute in overworld run function admg:damage/bin_decomposition
attribute @s generic.knockback_resistance modifier remove ebfd378c-f0ad-4b56-a448-c105c4078ca4