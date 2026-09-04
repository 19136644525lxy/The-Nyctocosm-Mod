#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation CraftIndex value = @s craftindex
execute as @e[tag=dais,tag=root,tag=matchedid] run function crafting:dais/inculcate
scoreboard players reset @s craftindex
function crafting:dais/summon_trader
kill @s