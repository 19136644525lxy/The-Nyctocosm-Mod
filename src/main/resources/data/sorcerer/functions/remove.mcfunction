#built using mc-build (https://github.com/mc-build/mc-build)

function animated_java:ajsorcerer/remove/all
function animated_java:ajsorcererm1/remove/all
function animated_java:ajsorcererm2/remove/all
function animated_java:ajnullpoint/remove/all
kill @e[tag=SorcererPlane]
kill @e[tag=hitbox,tag=sorcerer]
kill @e[tag=sorcererattack]
kill @e[tag=sorcerer]
scoreboard players reset Sorcerer ns.health
scoreboard players reset Nullpoint ns.health
scoreboard players reset Sorcerer ns.phase
scoreboard players reset SorcererDying value
scoreboard players reset Sorcerer ns.attacking
scoreboard players reset Sorcerer ns.attack
scoreboard players reset Sorcerer ns.attackcooldown
scoreboard players reset SorcererIdle value
bossbar set minecraft:sorcerer players
tag @a remove ns.damagedsorcerer
execute as @a[tag=music.sorcerer] run function music:sorcerer/end