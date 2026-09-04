#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players reset @s click_fissure
execute unless score @s fissurecastcooldown matches 1.. run function items:nullfissure/cast