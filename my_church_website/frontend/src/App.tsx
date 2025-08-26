import React, { useEffect, useMemo, useState } from "react";
import { motion } from "framer-motion";
import {
  Church,
  CalendarDays,
  PlayCircle,
  Mail,
  MapPin,
  Phone,
  HeartHandshake,
  Menu,
  X,
  Clock,
  Send,
} from "lucide-react";

// ===== Utility fetcher with graceful fallback =====
async function api<T>(url: string, fallback: T): Promise<T> {
  try {
    const res = await fetch(url, { headers: { "Accept": "application/json" } });
    if (!res.ok) throw new Error("Bad status");
    const data = await res.json();
    return data as T;
  } catch {
    return fallback;
  }
}

// ===== Types =====
interface Sermon {
  id: string;
  title: string;
  speaker: string;
  date: string; // ISO string
  series?: string;
  thumbnail?: string;
  audioUrl?: string;
  videoUrl?: string;
  passage?: string;
}

interface EventItem {
  id: string;
  title: string;
  date: string; // ISO
  startTime?: string; // e.g. "10:30"
  endTime?: string;
  location?: string;
  description?: string;
  cover?: string;
  registrationUrl?: string;
}

interface Ministry {
  id: string;
  name: string;
  summary: string;
  icon?: string; // emoji or URL
  contactEmail?: string;
}

// ===== Mock fallbacks (used only when API not ready) =====
const FALLBACK_SERMONS: Sermon[] = [
  {
    id: "s1",
    title: "Hope in Every Season",
    speaker: "Pastor Kim",
    date: new Date().toISOString(),
    series: "Philippians",
    passage: "Philippians 4:4-9",
    thumbnail:
      "https://images.unsplash.com/photo-1496307042754-b4aa456c4a2d?q=80&w=2070&auto=format&fit=crop",
    videoUrl: "#",
  },
  {
    id: "s2",
    title: "Walking in Grace",
    speaker: "Elder Park",
    date: new Date(Date.now() - 1000 * 60 * 60 * 24 * 7).toISOString(),
    series: "Galatians",
    passage: "Galatians 5:1-12",
    thumbnail:
      "https://images.unsplash.com/photo-1504051771394-dd2e66b2e08f?q=80&w=2069&auto=format&fit=crop",
    audioUrl: "#",
  },
];

const FALLBACK_EVENTS: EventItem[] = [
  {
    id: "e1",
    title: "Sunday Worship (주일예배)",
    date: new Date().toISOString().slice(0, 10),
    startTime: "10:30",
    endTime: "12:00",
    location: "Main Sanctuary",
    description: "Join us for worship, Word, and fellowship.",
  },
  {
    id: "e2",
    title: "Youth Gathering",
    date: new Date(Date.now() + 1000 * 60 * 60 * 24 * 5)
      .toISOString()
      .slice(0, 10),
    startTime: "18:30",
    location: "Youth Hall",
    description: "Games, worship, and small groups.",
  },
];

const FALLBACK_MINISTRIES: Ministry[] = [
  {
    id: "m1",
    name: "Worship Team",
    summary: "Leading the congregation in praise each Sunday.",
    icon: "🎵",
    contactEmail: "worship@yourchurch.org",
  },
  {
    id: "m2",
    name: "Outreach",
    summary: "Serving our neighbors through monthly food drives.",
    icon: "🤝",
    contactEmail: "outreach@yourchurch.org",
  },
  {
    id: "m3",
    name: "Next Gen",
    summary: "Kids and youth ministries focused on discipleship.",
    icon: "👧🧒",
    contactEmail: "nextgen@yourchurch.org",
  },
];

// ===== Helper components =====
const SectionTitle: React.FC<{ icon?: React.ReactNode; title: string; subtitle?: string }>= ({ icon, title, subtitle }) => (
  <div className="mb-8 text-center">
    <div className="flex items-center justify-center gap-3">
      {icon}
      <h2 className="text-3xl md:text-4xl font-bold tracking-tight">{title}</h2>
    </div>
    {subtitle && (
      <p className="mt-3 text-muted-foreground max-w-2xl mx-auto">{subtitle}</p>
    )}
  </div>
);

// Format date nicely
const fmtDate = (iso?: string) => {
  if (!iso) return "";
  try {
    const d = new Date(iso);
    return d.toLocaleDateString(undefined, {
      year: "numeric",
      month: "short",
      day: "numeric",
      weekday: "short",
    });
  } catch {
    return iso;
  }
};

// ===== Main App =====
export default function ChurchWebsite() {
  const [openMenu, setOpenMenu] = useState(false);
  const [sermons, setSermons] = useState<Sermon[]>([]);
  const [events, setEvents] = useState<EventItem[]>([]);
  const [ministries, setMinistries] = useState<Ministry[]>([]);
  const [sending, setSending] = useState(false);
  const [sent, setSent] = useState<null | "ok" | "err">(null);

  useEffect(() => {
    api<Sermon[]>("/api/sermons", FALLBACK_SERMONS).then(setSermons);
    api<EventItem[]>("/api/events", FALLBACK_EVENTS).then(setEvents);
    api<Ministry[]>("/api/ministries", FALLBACK_MINISTRIES).then(setMinistries);
  }, []);

  const nextEvent = useMemo(() => {
    const upcoming = events
      .map((e) => ({ ...e, ts: new Date(e.date).getTime() }))
      .filter((e) => !isNaN(e.ts) && e.ts >= Date.now())
      .sort((a, b) => a.ts - b.ts);
    return upcoming[0];
  }, [events]);

  async function onContactSubmit(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();
    setSending(true);
    setSent(null);
    const form = new FormData(e.currentTarget);
    const payload = Object.fromEntries(form.entries());
    try {
      const res = await fetch("/api/contact", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });
      if (!res.ok) throw new Error("fail");
      setSent("ok");
      e.currentTarget.reset();
    } catch (err) {
      setSent("err");
    } finally {
      setSending(false);
    }
  }

  // Smooth scroll helper
  function scrollToId(id: string) {
    const el = document.getElementById(id);
    if (el) {
      el.scrollIntoView({ behavior: "smooth", block: "start" });
      setOpenMenu(false);
    }
  }

  return (
    <div className="min-h-screen bg-gradient-to-b from-slate-50 to-white text-slate-900">
      {/* NAVBAR */}
      <header className="sticky top-0 z-50 backdrop-blur bg-white/70 border-b">
        <div className="max-w-6xl mx-auto px-4 py-3 flex items-center justify-between">
          <button
            onClick={() => scrollToId("home")}
            className="flex items-center gap-2 font-semibold text-xl"
          >
            <Church className="w-6 h-6" />
            <span>Your Church</span>
          </button>

          <nav className="hidden md:flex gap-6 text-sm font-medium">
            {[
              ["Home", "home"],
              ["Sermons", "sermons"],
              ["Events", "events"],
              ["Ministries", "ministries"],
              ["Give", "give"],
              ["Visit", "visit"],
              ["Contact", "contact"],
            ].map(([label, id]) => (
              <button key={id} className="hover:text-slate-600" onClick={() => scrollToId(String(id))}>
                {label}
              </button>
            ))}
          </nav>

          <button
            className="md:hidden rounded-xl p-2 border"
            onClick={() => setOpenMenu((v) => !v)}
            aria-label="Toggle Menu"
          >
            {openMenu ? <X className="w-5 h-5" /> : <Menu className="w-5 h-5" />}
          </button>
        </div>
        {openMenu && (
          <div className="md:hidden border-t bg-white">
            <div className="max-w-6xl mx-auto px-4 py-2 grid grid-cols-2 gap-2">
              {[
                ["Home", "home"],
                ["Sermons", "sermons"],
                ["Events", "events"],
                ["Ministries", "ministries"],
                ["Give", "give"],
                ["Visit", "visit"],
                ["Contact", "contact"],
              ].map(([label, id]) => (
                <button
                  key={id}
                  className="py-2 px-3 rounded-xl text-left bg-slate-50 hover:bg-slate-100"
                  onClick={() => scrollToId(String(id))}
                >
                  {label}
                </button>
              ))}
            </div>
          </div>
        )}
      </header>

      {/* HERO */}
      <section id="home" className="relative overflow-hidden">
        <div
          className="absolute inset-0 -z-10 bg-cover bg-center"
          style={{
            backgroundImage:
              "url(https://images.unsplash.com/photo-1529070538774-1843cb3265df?q=80&w=2070&auto=format&fit=crop)",
          }}
        />
        <div className="absolute inset-0 -z-10 bg-white/70" />
        <div className="max-w-6xl mx-auto px-4 py-24 md:py-32">
          <motion.h1
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.6 }}
            className="text-4xl md:text-6xl font-extrabold tracking-tight"
          >
            Welcome Home.
          </motion.h1>
          <motion.p
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.7, delay: 0.05 }}
            className="mt-5 max-w-2xl text-lg md:text-xl text-slate-700"
          >
            A community following Jesus, loving neighbors, and seeking the peace of our city.
          </motion.p>
          <div className="mt-8 flex flex-wrap gap-3">
            <a
              href="#sermons"
              onClick={(e) => {
                e.preventDefault();
                scrollToId("sermons");
              }}
              className="inline-flex items-center gap-2 rounded-2xl px-5 py-3 bg-slate-900 text-white hover:bg-slate-800 shadow"
            >
              <PlayCircle className="w-5 h-5" /> Latest Sermon
            </a>
            {nextEvent && (
              <a
                href="#events"
                onClick={(e) => {
                  e.preventDefault();
                  scrollToId("events");
                }}
                className="inline-flex items-center gap-2 rounded-2xl px-5 py-3 border hover:bg-white/60"
              >
                <CalendarDays className="w-5 h-5" /> Next: {nextEvent.title}
              </a>
            )}
          </div>
        </div>
      </section>

      {/* SERMONS */}
      <section id="sermons" className="max-w-6xl mx-auto px-4 py-16">
        <SectionTitle
          icon={<PlayCircle className="w-7 h-7" />}
          title="Sermons"
          subtitle="Watch or listen to recent messages."
        />
        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
          {sermons.map((s) => (
            <motion.article
              key={s.id}
              initial={{ opacity: 0, y: 10 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true, amount: 0.2 }}
              className="rounded-2xl overflow-hidden border bg-white shadow-sm hover:shadow-md transition-shadow"
            >
              {s.thumbnail && (
                <div className="aspect-video bg-slate-200">
                  <img src={s.thumbnail} alt={s.title} className="w-full h-full object-cover" />
                </div>
              )}
              <div className="p-4">
                <h3 className="font-semibold text-lg line-clamp-2">{s.title}</h3>
                <p className="text-sm text-slate-600 mt-1">
                  {s.speaker} · {fmtDate(s.date)} {s.series ? `· ${s.series}` : ""}
                </p>
                {s.passage && (
                  <p className="text-sm text-slate-500 mt-2">Text: {s.passage}</p>
                )}
                <div className="mt-4 flex gap-2">
                  {s.videoUrl && (
                    <a
                      href={s.videoUrl}
                      className="px-3 py-2 rounded-xl text-sm bg-slate-900 text-white"
                    >
                      Watch
                    </a>
                  )}
                  {s.audioUrl && (
                    <a
                      href={s.audioUrl}
                      className="px-3 py-2 rounded-xl text-sm border"
                    >
                      Listen
                    </a>
                  )}
                </div>
              </div>
            </motion.article>
          ))}
        </div>
      </section>

      {/* EVENTS */}
      <section id="events" className="max-w-6xl mx-auto px-4 py-16 bg-gradient-to-b from-white to-slate-50 rounded-3xl">
        <SectionTitle
          icon={<CalendarDays className="w-7 h-7" />}
          title="Events"
          subtitle="Gatherings and upcoming activities."
        />
        <div className="grid md:grid-cols-2 gap-6">
          {events.map((ev) => (
            <div key={ev.id} className="rounded-2xl border bg-white p-5 shadow-sm">
              <div className="flex items-start justify-between gap-3">
                <div>
                  <h3 className="font-semibold text-lg">{ev.title}</h3>
                  <p className="text-sm text-slate-600 mt-1">
                    {fmtDate(ev.date)} {ev.startTime ? `· ${ev.startTime}` : ""}
                    {ev.endTime ? ` – ${ev.endTime}` : ""}
                    {ev.location ? ` · ${ev.location}` : ""}
                  </p>
                </div>
                <Clock className="w-5 h-5 text-slate-400" />
              </div>
              {ev.description && (
                <p className="text-sm text-slate-700 mt-3">{ev.description}</p>
              )}
              <div className="mt-4 flex gap-2">
                {ev.registrationUrl && (
                  <a href={ev.registrationUrl} className="px-3 py-2 rounded-xl text-sm bg-slate-900 text-white">
                    Register
                  </a>
                )}
              </div>
            </div>
          ))}
        </div>
      </section>

      {/* MINISTRIES */}
      <section id="ministries" className="max-w-6xl mx-auto px-4 py-16">
        <SectionTitle
          icon={<HeartHandshake className="w-7 h-7" />}
          title="Ministries"
          subtitle="Find your place to serve and grow."
        />
        <div className="grid sm:grid-cols-2 lg:grid-cols-3 gap-6">
          {ministries.map((m) => (
            <div key={m.id} className="rounded-2xl border p-5 bg-white shadow-sm hover:shadow-md transition">
              <div className="text-3xl">{m.icon ?? "✨"}</div>
              <h3 className="mt-2 font-semibold text-lg">{m.name}</h3>
              <p className="text-sm text-slate-700 mt-2">{m.summary}</p>
              {m.contactEmail && (
                <a
                  href={`mailto:${m.contactEmail}`}
                  className="inline-block mt-3 text-sm underline hover:no-underline"
                >
                  Contact
                </a>
              )}
            </div>
          ))}
        </div>
      </section>

      {/* GIVE */}
      <section id="give" className="max-w-6xl mx-auto px-4 py-16">
        <div className="rounded-3xl border bg-gradient-to-r from-slate-900 to-slate-800 text-white p-8 md:p-12 flex flex-col md:flex-row gap-6 items-center justify-between shadow-lg">
          <div>
            <h3 className="text-2xl md:text-3xl font-bold">Partner with the Mission</h3>
            <p className="mt-2 text-white/80 max-w-xl">
              Your generosity helps us serve our city and share the gospel near and far.
            </p>
          </div>
          <a
            href="/give" // Backend: redirect or render giving page
            className="inline-flex items-center gap-2 rounded-2xl bg-white text-slate-900 px-6 py-3 font-semibold shadow hover:bg-white/90"
          >
            Give Online
          </a>
        </div>
      </section>

      {/* VISIT */}
      <section id="visit" className="max-w-6xl mx-auto px-4 py-16">
        <SectionTitle icon={<MapPin className="w-7 h-7" />} title="Visit Us" subtitle="We'd love to meet you!" />
        <div className="grid lg:grid-cols-2 gap-6 items-stretch">
          <div className="rounded-2xl border bg-white p-6 shadow-sm">
            <h4 className="font-semibold text-lg">Service Times</h4>
            <ul className="mt-3 text-sm text-slate-700 space-y-1">
              <li>Sunday Worship: 10:30 AM</li>
              <li>Wednesday Prayer: 7:30 PM</li>
              <li>Youth: Friday 7:00 PM</li>
            </ul>
            <h4 className="font-semibold text-lg mt-6">Address</h4>
            <p className="text-sm text-slate-700 mt-1">123 Hope St, Seoul</p>
            <div className="mt-4 flex flex-wrap gap-3 text-sm">
              <a href="tel:+821012345678" className="inline-flex items-center gap-2 px-3 py-2 rounded-xl border">
                <Phone className="w-4 h-4" /> +82 10-1234-5678
              </a>
              <a href="mailto:info@yourchurch.org" className="inline-flex items-center gap-2 px-3 py-2 rounded-xl border">
                <Mail className="w-4 h-4" /> info@yourchurch.org
              </a>
            </div>
          </div>
          <div className="rounded-2xl overflow-hidden border bg-white">
            {/* Replace the src with your embedded map */}
            <iframe
              title="Map"
              className="w-full h-[320px] md:h-full"
              loading="lazy"
              referrerPolicy="no-referrer-when-downgrade"
              src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d25313.87396438262!2d126.9782911!3d37.5666791!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x357ca2f2ecf0cbbf%3A0x7a0d1ddf3e6dbb!2sSeoul!5e0!3m2!1sen!2skr!4v1682688290000"
              allowFullScreen
            />
          </div>
        </div>
      </section>

      {/* CONTACT */}
      <section id="contact" className="max-w-6xl mx-auto px-4 py-16">
        <SectionTitle title="Contact Us" subtitle="Questions or prayer requests? Send us a note." />
        <form onSubmit={onContactSubmit} className="max-w-2xl mx-auto grid gap-4">
          <div className="grid md:grid-cols-2 gap-4">
            <div>
              <label className="text-sm font-medium">Name</label>
              <input name="name" required className="mt-1 w-full rounded-xl border px-3 py-2" />
            </div>
            <div>
              <label className="text-sm font-medium">Email</label>
              <input name="email" type="email" required className="mt-1 w-full rounded-xl border px-3 py-2" />
            </div>
          </div>
          <div>
            <label className="text-sm font-medium">Message</label>
            <textarea name="message" required rows={5} className="mt-1 w-full rounded-xl border px-3 py-2" />
          </div>
          <div className="flex items-center gap-3">
            <button
              disabled={sending}
              className="inline-flex items-center gap-2 rounded-2xl px-5 py-3 bg-slate-900 text-white hover:bg-slate-800 disabled:opacity-60"
            >
              <Send className="w-4 h-4" /> {sending ? "Sending..." : "Send"}
            </button>
            {sent === "ok" && <span className="text-sm text-green-600">Thanks! We'll get back to you.</span>}
            {sent === "err" && <span className="text-sm text-red-600">Something went wrong. Please try again.</span>}
          </div>
        </form>
      </section>

      {/* FOOTER */}
      <footer className="border-t">
        <div className="max-w-6xl mx-auto px-4 py-10 text-sm text-slate-600 flex flex-col md:flex-row items-center justify-between gap-3">
          <div className="flex items-center gap-2">
            <Church className="w-4 h-4" /> Your Church · © {new Date().getFullYear()}
          </div>
          <div className="flex items-center gap-4">
            <a className="hover:underline" href="#visit" onClick={(e) => { e.preventDefault(); scrollToId("visit"); }}>Plan a Visit</a>
            <a className="hover:underline" href="#give" onClick={(e) => { e.preventDefault(); scrollToId("give"); }}>Give</a>
            <a className="hover:underline" href="#contact" onClick={(e) => { e.preventDefault(); scrollToId("contact"); }}>Contact</a>
          </div>
        </div>
      </footer>
    </div>
  );
}
