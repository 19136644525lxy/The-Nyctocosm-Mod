execute store result score $amount admg run data get storage admg:data heal.amount 10
execute store result score $health admg run data get entity @s Health 10
execute store result score @s admg run attribute @s generic.max_health get 10

# hp = 10, max = 20, heal = 5
# dmg to apply = 
# max - (hp+heal)
# dmg = max - hp - heal

scoreboard players operation @s admg -= $health admg 
scoreboard players operation @s admg -= $amount admg 
effect give @s instant_health 1 20 true
tag @s add admg.heal
schedule function admg:heal/schedule 2t