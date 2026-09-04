#built using mc-build (https://github.com/mc-build/mc-build)

function animated_java:ajsorcererm2/summon
execute as @e[tag=aj.ajsorcererm2.root] at @s run function sorcerer:__generated__/block/206
function animated_java:ajsorcererm1/remove/this
execute at @e[tag=aj.ajnullpoint.root] run particle squid_ink ~ ~2 ~ 0.2 0.7 0.2 0 300 force
function animated_java:ajnullpoint/remove/all
tag @e[tag=sorcererhitbox] remove invul