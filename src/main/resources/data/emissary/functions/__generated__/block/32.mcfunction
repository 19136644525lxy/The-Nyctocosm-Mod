#built using mc-build (https://github.com/mc-build/mc-build)

execute as @a[tag=em.damagedemissary] at @s run playsound nyctocosm:boss.destroy master @s ~ ~ ~
title @a[tag=em.damagedemissary] times 20 100 20
title @a[tag=em.damagedemissary] title {"translate":"entity.nyctocosm.celestial_defeated","color":"gold","with":[{"translate":"entity.nyctocosm.celestial_defeated1","color":"white"}]}
function lore:storylines/adherent/emissary