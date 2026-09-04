#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation 1 temp = a.temp a.value
scoreboard players operation 1 temp *= #2 const
scoreboard players operation 0 temp = a.amelioration.healAmt a.value
scoreboard players operation 0 temp += 1 temp
scoreboard players operation 0 temp *= #1000 const
scoreboard players operation @s ncd_heal = 0 temp
function ncdamage:apply_changes
effect give @s saturation 3 0 true
particle firework ~ ~1 ~ 0.2 0.2 0.2 0.2 50 force